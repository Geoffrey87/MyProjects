package com.LegisTrack.LegisTrack.service;

import com.LegisTrack.LegisTrack.Dto.VoteCountDto;
import com.LegisTrack.LegisTrack.Dto.VoteDto;
import com.LegisTrack.LegisTrack.Dto.VoteInputDto;

import java.util.List;

/**
 * Service interface for handling vote operations.
 */
public interface IVoteService {

    /**
     * Creates a new vote based on the provided input DTO.
     *
     * @param voteInputDto the input DTO containing vote details.
     * @return the created vote as a VoteDto.
     */
    VoteDto createVote(VoteInputDto voteInputDto);

    /**
     * Retrieves a vote by its ID.
     *
     * @param id the ID of the vote.
     * @return the vote as a VoteDto.
     */
    VoteDto getVoteById(Long id);

    /**
     * Retrieves all votes.
     *
     * @return a list of VoteDto.
     */
    List<VoteDto> getAllVotes();

    /**
     * Retrieves aggregated vote counts for a given law.
     *
     * @param lawId the law's ID.
     * @return the vote counts as a VoteCountDto.
     */
    VoteCountDto getVoteCountsByLaw(Long lawId);
}

