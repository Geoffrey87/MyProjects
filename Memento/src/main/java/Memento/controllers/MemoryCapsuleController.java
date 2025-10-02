package Memento.controllers;

import Memento.dtos.InputDto.MemoryCapsuleCreateDto;
import Memento.dtos.OutputDto.MemoryCapsuleOutputDto;
import Memento.security.SecurityUserDetails;
import Memento.services.IMemoryCapsuleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/capsules")
public class MemoryCapsuleController {

    private final IMemoryCapsuleService capsuleService;

    public MemoryCapsuleController(IMemoryCapsuleService capsuleService) {
        this.capsuleService = capsuleService;
    }

    // ---------------------------
    // CREATE
    // ---------------------------
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<MemoryCapsuleOutputDto> createCapsule(
            @Valid @RequestBody MemoryCapsuleCreateDto dto,
            @AuthenticationPrincipal SecurityUserDetails userDetails
    ) {
        MemoryCapsuleOutputDto created = capsuleService.create(dto, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ---------------------------
    // RETRIEVE
    // ---------------------------
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<MemoryCapsuleOutputDto> getCapsuleById(@PathVariable Long id) {
        MemoryCapsuleOutputDto capsule = capsuleService.getById(id);
        return ResponseEntity.ok(capsule);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<MemoryCapsuleOutputDto>> getMyCapsules(
            @AuthenticationPrincipal SecurityUserDetails userDetails
    ) {
        List<MemoryCapsuleOutputDto> capsules = capsuleService.getByUser(userDetails.getUsername());
        return ResponseEntity.ok(capsules);
    }

    // ---------------------------
    // PUBLISH
    // ---------------------------
    @PutMapping("/{id}/publish")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<MemoryCapsuleOutputDto> publishCapsule(
            @PathVariable Long id,
            @AuthenticationPrincipal SecurityUserDetails userDetails
    ) {
        MemoryCapsuleOutputDto published = capsuleService.publish(id, userDetails.getUsername());
        return ResponseEntity.ok(published);
    }
}

