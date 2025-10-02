package Memento.controllers;

import Memento.dtos.InputDto.CommentCreateDto;
import Memento.dtos.OutputDto.CommentOutputDto;
import Memento.entities.enums.CommentTargetType;
import Memento.security.SecurityUserDetails;
import Memento.services.ICommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final ICommentService commentService;

    public CommentController(ICommentService commentService) {
        this.commentService = commentService;
    }

    // ---------------------------
    // CREATE
    // ---------------------------
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CommentOutputDto> createComment(
            @Valid @RequestBody CommentCreateDto dto,
            @AuthenticationPrincipal SecurityUserDetails userDetails
    ) {
        CommentOutputDto created = commentService.create(dto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ---------------------------
    // GET COMMENTS BY TARGET
    // ---------------------------
    @GetMapping("/{targetType}/{targetId}")
    public ResponseEntity<List<CommentOutputDto>> getCommentsByTarget(
            @PathVariable CommentTargetType targetType,
            @PathVariable Long targetId
    ) {
        List<CommentOutputDto> comments = commentService.getByTarget(targetId, targetType);
        return ResponseEntity.ok(comments);
    }

    // ---------------------------
    // DELETE COMMENT
    // ---------------------------
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long id,
            @AuthenticationPrincipal SecurityUserDetails userDetails
    ) {
        commentService.delete(id, userDetails.getUser());
        return ResponseEntity.noContent().build();
    }
}

