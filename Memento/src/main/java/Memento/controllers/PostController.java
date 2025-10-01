package Memento.controllers;

import Memento.dtos.InputDto.PostCreateDto;
import Memento.dtos.OutputDto.PostOutputDto;
import Memento.security.SecurityUserDetails;
import Memento.services.IPostService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final IPostService postService;

    public PostController(IPostService postService) {
        this.postService = postService;
    }

    /**
     * Create a new post (only authenticated users).
     */
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PostOutputDto> create(
            @Valid @RequestBody PostCreateDto dto,
            @AuthenticationPrincipal SecurityUserDetails userDetails) {
        PostOutputDto created = postService.create(dto, userDetails.getUsername());
        return ResponseEntity.ok(created);
    }

    /**
     * Update an existing post (only the author can update).
     */
    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PostOutputDto> update(
            @PathVariable Long id,
            @Valid @RequestBody PostCreateDto dto,
            @AuthenticationPrincipal SecurityUserDetails userDetails) {
        PostOutputDto updated = postService.update(id, dto, userDetails.getUsername());
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete a post (only the author can delete).
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal SecurityUserDetails userDetails) {
        postService.delete(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

    /**
     * Get a post by ID (public endpoint).
     */
    @GetMapping("/{id}")
    public ResponseEntity<PostOutputDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getById(id));
    }

    /**
     * Get timeline posts (authenticated user only).
     */
    @GetMapping("/timeline")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<PostOutputDto>> getTimeline(
            @AuthenticationPrincipal SecurityUserDetails userDetails) {
        return ResponseEntity.ok(postService.getTimeline(userDetails.getUsername()));
    }

    /**
     * Get all posts from a specific user (public endpoint).
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostOutputDto>> getByUser(
            @PathVariable Long userId,
            @AuthenticationPrincipal SecurityUserDetails userDetails) {
        return ResponseEntity.ok(postService.getByUser(userId, userDetails.getUsername()));
    }
}

