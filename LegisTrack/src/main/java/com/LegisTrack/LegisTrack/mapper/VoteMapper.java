package com.LegisTrack.LegisTrack.mapper;

import com.LegisTrack.LegisTrack.Dto.VoteDto;
import com.LegisTrack.LegisTrack.Dto.VoteInputDto;
import com.LegisTrack.LegisTrack.entity.Vote;
import com.LegisTrack.LegisTrack.entity.VoteType;
import org.springframework.stereotype.Component;

/**
 * This class is used to map the Vote entity to the VoteDto and vice versa
 */
@Component
public class VoteMapper {

    public static VoteDto domainToDto(Vote vote, VoteDto voteDto){
        voteDto.setId(vote.getId());
        voteDto.setVoteType(vote.getVoteType().name());
        voteDto.setDescription(vote.getLaw().getDescription());
        voteDto.setLawId(vote.getLaw().getId());
        return voteDto;
    }

    public static Vote dtoToDomain(VoteInputDto voteInputDto, Vote vote){
        vote.setVoteType(VoteType.valueOf(voteInputDto.getVoteType()));
        return vote;
    }
}
