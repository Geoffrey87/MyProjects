package com.LegisTrack.LegisTrack.service.impl;

import com.LegisTrack.LegisTrack.Dto.VoteCountDto;
import com.LegisTrack.LegisTrack.Dto.VoteDto;
import com.LegisTrack.LegisTrack.Dto.VoteInputDto;
import com.LegisTrack.LegisTrack.entity.Law;
import com.LegisTrack.LegisTrack.entity.Vote;
import com.LegisTrack.LegisTrack.entity.VoteType;
import com.LegisTrack.LegisTrack.mapper.VoteMapper;
import com.LegisTrack.LegisTrack.repository.LawRepo;
import com.LegisTrack.LegisTrack.repository.VoteRepo;
import com.LegisTrack.LegisTrack.service.IVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of IVoteService for handling vote operations.
 */
@Service
public class VoteService implements IVoteService {

    private final VoteRepo voteRepo;
    private final LawRepo lawRepo;

    @Autowired
    public VoteService(VoteRepo voteRepo, LawRepo lawRepo) {
        this.voteRepo = voteRepo;
        this.lawRepo = lawRepo;
    }

    /**
     * Creates a new vote.
     */
    @Override
    public VoteDto createVote(VoteInputDto voteInputDto) {
        Vote vote = new Vote();
        // Convert the vote type from String to the VoteType enum.
        vote.setVoteType(VoteType.valueOf(voteInputDto.getVoteType()));

        // Retrieve the Law entity from the repository using the lawId from the DTO.
        Law law = lawRepo.findById(voteInputDto.getLawId())
                .orElseThrow(() -> new RuntimeException("Law not found with ID: " + voteInputDto.getLawId()));
        vote.setLaw(law);

        // Save the new vote.
        vote = voteRepo.save(vote);

        // Map the Vote entity to VoteDto and return.
        return VoteMapper.domainToDto(vote, new VoteDto());
    }

    /**
     * Retrieves a vote by its ID.
     */
    @Override
    public VoteDto getVoteById(Long id) {
        Vote vote = voteRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Vote not found with ID: " + id));
        return VoteMapper.domainToDto(vote, new VoteDto());
    }

    /**
     * Retrieves all votes.
     */
    @Override
    public List<VoteDto> getAllVotes() {
        List<Vote> votes = voteRepo.findAll();
        List<VoteDto> voteDtos = new ArrayList<>();
        for (Vote vote : votes) {
            voteDtos.add(VoteMapper.domainToDto(vote, new VoteDto()));
        }
        return voteDtos;
    }

    /**
     * Retrieves aggregated vote counts for a given law.
     */
    @Override
    public VoteCountDto getVoteCountsByLaw(Long lawId) {
        List<Vote> votes = voteRepo.findByLawId(lawId);
        int inFavor = 0;
        int against = 0;
        int abstention = 0;
        for (Vote vote : votes) {
            switch (vote.getVoteType()) {
                case IN_FAVOR:
                    inFavor++;
                    break;
                case AGAINST:
                    against++;
                    break;
                case ABSTENTION:
                    abstention++;
                    break;
            }
        }
        return new VoteCountDto(inFavor, against, abstention);
    }
}

