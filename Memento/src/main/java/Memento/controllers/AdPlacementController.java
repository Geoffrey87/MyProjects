package Memento.controllers;

import Memento.dtos.InputDto.AdPlacementCreateDto;
import Memento.dtos.OutputDto.AdPlacementOutputDto;
import Memento.services.IAdPlacementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ads")
public class AdPlacementController {

    private final IAdPlacementService adPlacementService;

    @Autowired
    public AdPlacementController(IAdPlacementService adPlacementService) {
        this.adPlacementService = adPlacementService;
    }

    // ---------------------------
    // PUBLIC ENDPOINTS
    // ---------------------------

    @GetMapping("/active")
    public ResponseEntity<List<AdPlacementOutputDto>> getActiveAds() {
        return ResponseEntity.ok(adPlacementService.getActiveAds());
    }

    @GetMapping("/active/category/{categoryId}")
    public ResponseEntity<List<AdPlacementOutputDto>> getActiveAdsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(adPlacementService.getActiveAdsByCategory(categoryId));
    }

    @GetMapping("/active/tag/{tagId}")
    public ResponseEntity<List<AdPlacementOutputDto>> getActiveAdsByTag(@PathVariable Long tagId) {
        return ResponseEntity.ok(adPlacementService.getActiveAdsByTag(tagId));
    }

    // ---------------------------
    // ADMIN ENDPOINTS
    // ---------------------------

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdPlacementOutputDto> createAd(@Valid @RequestBody AdPlacementCreateDto dto) {
        AdPlacementOutputDto createdAd = adPlacementService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAd);
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdPlacementOutputDto> approveAd(@PathVariable Long id) {
        return ResponseEntity.ok(adPlacementService.approve(id));
    }

    @PutMapping("/{id}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdPlacementOutputDto> activateAd(@PathVariable Long id) {
        return ResponseEntity.ok(adPlacementService.activate(id));
    }

    @PutMapping("/{id}/expire")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdPlacementOutputDto> expireAd(@PathVariable Long id) {
        return ResponseEntity.ok(adPlacementService.expire(id));
    }
}

