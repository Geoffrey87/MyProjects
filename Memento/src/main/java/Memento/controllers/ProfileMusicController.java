package Memento.controllers;

import Memento.dtos.InputDto.ProfileMusicCreateDto;
import Memento.dtos.OutputDto.ProfileMusicOutputDto;
import Memento.security.SecurityUserDetails;
import Memento.services.IProfileMusicService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile-music")
public class ProfileMusicController {

    private final IProfileMusicService profileMusicService;

    public ProfileMusicController(IProfileMusicService profileMusicService) {
        this.profileMusicService = profileMusicService;
    }

    // ---------------------------
    // CREATE
    // ---------------------------
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ProfileMusicOutputDto> addProfileMusic(
            @AuthenticationPrincipal SecurityUserDetails userDetails,
            @Valid @RequestBody ProfileMusicCreateDto dto
    ) {
        ProfileMusicOutputDto created = profileMusicService.addProfileMusic(userDetails.getUsername(), dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ---------------------------
    // GET ACTIVE
    // ---------------------------
    @GetMapping("/active/{userId}")
    public ResponseEntity<ProfileMusicOutputDto> getActiveProfileMusic(@PathVariable Long userId) {
        ProfileMusicOutputDto active = profileMusicService.getActiveProfileMusic(userId);
        return ResponseEntity.ok(active);
    }

    // ---------------------------
    // GET ALL
    // ---------------------------
    @GetMapping("/all/{userId}")
    public ResponseEntity<List<ProfileMusicOutputDto>> getAllProfileMusic(@PathVariable Long userId) {
        List<ProfileMusicOutputDto> musics = profileMusicService.getAllProfileMusic(userId);
        return ResponseEntity.ok(musics);
    }

    // ---------------------------
    // DEACTIVATE
    // ---------------------------
    @PutMapping("/{id}/deactivate")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deactivateProfileMusic(
            @PathVariable Long id,
            @AuthenticationPrincipal SecurityUserDetails userDetails
    ) {
        profileMusicService.deactivateProfileMusic(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}

