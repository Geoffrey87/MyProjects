package com.LegisTrack.LegisTrack.controller;

import com.LegisTrack.LegisTrack.Dto.VoteCountDto;
import com.LegisTrack.LegisTrack.Dto.VoteDto;
import com.LegisTrack.LegisTrack.Dto.VoteInputDto;
import com.LegisTrack.LegisTrack.service.IVoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/votes")
public class VoteController {

    private final IVoteService voteService;

    public VoteController(IVoteService voteService) {
        this.voteService = voteService;
    }

    /**
     * Creates a new vote.
     * @param voteInputDto the input DTO containing vote details.
     * @return the created vote as a VoteDto.
     */
    @PostMapping
    public ResponseEntity<VoteDto> createVote(@RequestBody VoteInputDto voteInputDto) {
        VoteDto createdVote = voteService.createVote(voteInputDto);
        return new ResponseEntity<>(createdVote, HttpStatus.CREATED);
    }

    /**
     * Retrieves a vote by its ID.
     * @param id the vote's ID.
     * @return the vote as a VoteDto.
     */
    @GetMapping("/{id}")
    public ResponseEntity<VoteDto> getVoteById(@PathVariable Long id) {
        VoteDto voteDto = voteService.getVoteById(id);
        return ResponseEntity.ok(voteDto);
    }

    /**
     * Retrieves all votes.
     * @return a list of VoteDto.
     */
    @GetMapping
    public ResponseEntity<List<VoteDto>> getAllVotes() {
        List<VoteDto> votes = voteService.getAllVotes();
        return ResponseEntity.ok(votes);
    }

    /**
     * Retrieves aggregated vote counts for a given law.
     * @param lawId the law's ID.
     * @return the vote counts as a VoteCountDto.
     */
    @GetMapping("/law/{lawId}/counts")
    public ResponseEntity<VoteCountDto> getVoteCountsByLaw(@PathVariable Long lawId) {
        VoteCountDto voteCountDto = voteService.getVoteCountsByLaw(lawId);
        return ResponseEntity.ok(voteCountDto);
    }
}

