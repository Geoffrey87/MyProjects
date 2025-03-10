package com.LegisTrack.LegisTrack.controller;

import com.LegisTrack.LegisTrack.Dto.PartyDto;
import com.LegisTrack.LegisTrack.Dto.PartyInputDto;
import com.LegisTrack.LegisTrack.service.IPartyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parties")
public class PartyController {

    private final IPartyService partyService;

    public PartyController(IPartyService partyService) {
        this.partyService = partyService;
    }

    /**
     * Creates a new Party for the given user.
     *
     * @param userId          The ID of the user (extracted from the header "X-User-Id")
     * @param partyInputDto   The PartyInputDto containing the data for creating a new Party.
     * @return The created Party as a PartyDto.
     */
    @PostMapping
    public ResponseEntity<PartyDto> createParty(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody PartyInputDto partyInputDto) {
        PartyDto createdParty = partyService.createPartyByUserId(partyInputDto, userId);
        return new ResponseEntity<>(createdParty, HttpStatus.CREATED);
    }

    /**
     * Retrieves a Party by its ID for the given user.
     *
     * @param userId The ID of the user.
     * @param id     The ID of the Party.
     * @return The Party as a PartyDto.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PartyDto> getPartyById(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable Long id) {
        PartyDto partyDto = partyService.getPartyByIdAndUserId(id, userId);
        return ResponseEntity.ok(partyDto);
    }

    /**
     * Retrieves all Parties for the given user.
     *
     * @param userId The ID of the user.
     * @return A list of PartyDto.
     */
    @GetMapping
    public ResponseEntity<List<PartyDto>> getAllParties(
            @RequestHeader("X-User-Id") String userId) {
        // Ensure that if the user has no parties yet, they get a copy from GLOBAL data.
        partyService.ensureUserHasParties(userId);
        List<PartyDto> parties = partyService.getAllParties(userId);
        return ResponseEntity.ok(parties);
    }

    /**
     * Increments the number of deputies of a Party for the given user.
     */
    @PutMapping("/{id}/incrementDeputies")
    public ResponseEntity<PartyDto> incrementDeputies(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable Long id,
            @RequestParam int count) {
        PartyDto updatedParty = partyService.incrementDeputies(id, count, userId);
        return ResponseEntity.ok(updatedParty);
    }

    /**
     * Decrements the number of deputies of a Party for the given user.
     */
    @PutMapping("/{id}/decrementDeputies")
    public ResponseEntity<PartyDto> decrementDeputies(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable Long id,
            @RequestParam int count) {
        PartyDto updatedParty = partyService.decrementDeputies(id, count, userId);
        return ResponseEntity.ok(updatedParty);
    }

    /**
     * Updates an existing Party for the given user.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PartyDto> updateParty(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable Long id,
            @RequestBody PartyInputDto partyInputDto) {
        PartyDto updatedParty = partyService.updateParty(id, partyInputDto, userId);
        return ResponseEntity.ok(updatedParty);
    }

    /**
     * Deletes a Party by its ID for the given user.
     *
     * @param userId The ID of the user.
     * @param id     The ID of the Party to delete.
     * @return An empty response with HTTP status 204.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParty(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable Long id) {
        partyService.deleteParty(id, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
