package com.LegisTrack.LegisTrack.service;

import com.LegisTrack.LegisTrack.Dto.PartyDto;
import com.LegisTrack.LegisTrack.Dto.PartyInputDto;
import java.util.List;

public interface IPartyService {

    public void ensureUserHasParties(String userId);

    PartyDto createPartyByUserId(PartyInputDto partyInputDTO, String userId);

    PartyDto getPartyByIdAndUserId(Long id, String userId);

    List<PartyDto> getAllParties(String userId);

    PartyDto incrementDeputies(Long partyId, int count, String userId);

    PartyDto decrementDeputies(Long partyId, int count, String userId);

    PartyDto updateParty(Long id, PartyInputDto partyInputDTO, String userId);

    void deleteParty(Long id, String userId);
}
