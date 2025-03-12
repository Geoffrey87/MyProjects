package com.LegisTrack.LegisTrack.service;

import com.LegisTrack.LegisTrack.Dto.VoteDto;
import com.LegisTrack.LegisTrack.Dto.VoteInputDto;
import com.LegisTrack.LegisTrack.entity.VoteType;

import java.util.List;
import java.util.Map;

/**
 * Service interface for handling vote operations.
 */
public interface IVoteService {

    /**
     * Generates random votes for a law and returns them as DTOs.
     *
     * @param lawId The ID of the law to generate votes for.
     * @return A list of generated VoteDto objects.
     */
    List<VoteDto> generateVotes(Long lawId, List<Long> partyIds);

    /**
     * Retrieves votes for a specific law grouped by vote type.
     *
     * @param lawId The ID of the law.
     * @return A map where keys are VoteTypes and values are lists of party names.
     */
    Map<VoteType, List<String>> getVotesByLaw(Long lawId);

}
