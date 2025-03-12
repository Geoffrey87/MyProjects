package com.LegisTrack.LegisTrack.controller;

import com.LegisTrack.LegisTrack.Dto.PartySelectionDto;
import com.LegisTrack.LegisTrack.Dto.VoteDto;
import com.LegisTrack.LegisTrack.Dto.VoteInputDto;
import com.LegisTrack.LegisTrack.entity.VoteType;
import com.LegisTrack.LegisTrack.service.IVoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/votes")
public class VoteController {
    private final IVoteService voteService;

    public VoteController(IVoteService voteService) {
        this.voteService = voteService;
    }

    /**
     * Generates random votes for a law and returns them as DTOs.
     */
    @PostMapping("/generate/{lawId}")
    public ResponseEntity<List<VoteDto>> generateVotes(
            @PathVariable Long lawId,
            @RequestBody PartySelectionDto request
    ) {
        List<VoteDto> votes = voteService.generateVotes(lawId, request.getPartyIds());
        return new ResponseEntity<>(votes, HttpStatus.CREATED);
    }



    /**
     * Returns votes for a specific law grouped by vote type.
     */
    @GetMapping("/law/{lawId}")
    public ResponseEntity<Map<VoteType, List<String>>> getVotesByLaw(
            @PathVariable Long lawId
    ) {
        Map<VoteType, List<String>> votes = voteService.getVotesByLaw(lawId);
        return ResponseEntity.ok(votes);
    }

}