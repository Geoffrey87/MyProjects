package Memento.controllers;

import Memento.dtos.InputDto.AlbumCreateDto;
import Memento.dtos.OutputDto.AlbumOutputDto;
import Memento.security.SecurityUserDetails;
import Memento.services.IAlbumService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/albums")
public class AlbumController {

    private final IAlbumService albumService;

    public AlbumController(IAlbumService albumService) {
        this.albumService = albumService;
    }

    // -----------------------------
    // Create
    // -----------------------------
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AlbumOutputDto> create(
            @Valid @RequestBody AlbumCreateDto dto,
            @AuthenticationPrincipal SecurityUserDetails userDetails) {

        AlbumOutputDto album = albumService.create(dto, userDetails.getUsername());
        return ResponseEntity.ok(album);
    }

    // -----------------------------
    // Update
    // -----------------------------
    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AlbumOutputDto> update(
            @PathVariable Long id,
            @Valid @RequestBody AlbumCreateDto dto,
            @AuthenticationPrincipal SecurityUserDetails userDetails) {

        AlbumOutputDto album = albumService.update(id, dto, userDetails.getUsername());
        return ResponseEntity.ok(album);
    }

    // -----------------------------
    // Delete
    // -----------------------------
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal SecurityUserDetails userDetails) {

        albumService.delete(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

    // -----------------------------
    // Get by ID
    // -----------------------------
    @GetMapping("/{id}")
    public ResponseEntity<AlbumOutputDto> getById(@PathVariable Long id) {
        AlbumOutputDto album = albumService.getById(id);
        return ResponseEntity.ok(album);
    }

    // -----------------------------
    // Get all albums from a user
    // -----------------------------
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AlbumOutputDto>> getByUser(@PathVariable Long userId) {
        List<AlbumOutputDto> albums = albumService.getByUser(userId);
        return ResponseEntity.ok(albums);
    }
}

