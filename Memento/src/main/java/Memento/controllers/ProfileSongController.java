package Memento.controllers;

import Memento.security.SecurityUserDetails;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile/song")
public class ProfileSongController {

    private final IProfileSongService profileSongService;

    public ProfileSongController(IProfileSongService profileSongService) {
        this.profileSongService = profileSongService;
    }

    // ---------------------------
    // UPDATE PROFILE SONG (auth user)
    // ---------------------------
    @PutMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ProfileSongOutputDto> updateProfileSong(
            @AuthenticationPrincipal SecurityUserDetails userDetails,
            @Valid @RequestBody ProfileSongUpdateDto dto
    ) {
        ProfileSongOutputDto updated = profileSongService.updateProfileSong(userDetails.getUsername(), dto);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    // ---------------------------
    // GET PROFILE SONG (public)
    // ---------------------------
    @GetMapping("/{userId}")
    public ResponseEntity<ProfileSongOutputDto> getProfileSong(@PathVariable Long userId) {
        ProfileSongOutputDto profileSong = profileSongService.getProfileSong(userId);
        return ResponseEntity.ok(profileSong);
    }
}

