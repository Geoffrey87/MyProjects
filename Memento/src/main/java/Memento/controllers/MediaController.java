package Memento.controllers;

import Memento.dtos.InputDto.MediaCreateDto;
import Memento.dtos.OutputDto.MediaOutputDto;
import Memento.security.SecurityUserDetails;
import Memento.services.IMediaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/media")
public class MediaController {

    private final IMediaService mediaService;

    public MediaController(IMediaService mediaService) {
        this.mediaService = mediaService;
    }

    /**
     * Upload a new media (photo or video).
     * The authenticated user becomes the owner.
     */
    @PostMapping("/upload")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<MediaOutputDto> upload(
            @Valid @RequestBody MediaCreateDto dto,
            @AuthenticationPrincipal SecurityUserDetails userDetails
    ) {
        return ResponseEntity.ok(mediaService.upload(dto, userDetails.getUsername()));
    }

    /**
     * Get details of a media item by id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MediaOutputDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(mediaService.getById(id));
    }

    /**
     * List all media uploaded by a given user.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MediaOutputDto>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(mediaService.getByUser(userId));
    }

    /**
     * Delete a media item (only if you are the owner).
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal SecurityUserDetails userDetails
    ) {
        mediaService.delete(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}

