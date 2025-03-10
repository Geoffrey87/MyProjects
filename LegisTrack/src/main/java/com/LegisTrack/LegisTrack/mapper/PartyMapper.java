package com.LegisTrack.LegisTrack.mapper;

import com.LegisTrack.LegisTrack.Dto.PartyDto;
import com.LegisTrack.LegisTrack.Dto.PartyInputDto;
import com.LegisTrack.LegisTrack.entity.Party;
import org.springframework.stereotype.Component;

/**
 * This class is used to map the Party entity to the PartyDto and vice versa
 */
@Component
public class PartyMapper {

    public static PartyDto domainToDto(Party party, PartyDto partyDto){
        partyDto.setId(party.getId());
        partyDto.setName(party.getName());
        partyDto.setNrOfDeputies(party.getNrOfDeputies());
        partyDto.setUserId(party.getUserId());
        return partyDto;
    }

    public static Party dtoToDomain(PartyInputDto partyInputDto, Party party) {
        party.setName(partyInputDto.getName());
        party.setNrOfDeputies(partyInputDto.getNrOfDeputies());
        return party;
    }
}
