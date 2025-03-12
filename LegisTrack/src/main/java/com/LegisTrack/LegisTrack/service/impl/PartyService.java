package com.LegisTrack.LegisTrack.service.impl;

import com.LegisTrack.LegisTrack.Dto.PartyDto;
import com.LegisTrack.LegisTrack.Dto.PartyInputDto;
import com.LegisTrack.LegisTrack.entity.Party;
import com.LegisTrack.LegisTrack.exception.BusinessException;
import com.LegisTrack.LegisTrack.mapper.PartyMapper;
import com.LegisTrack.LegisTrack.repository.PartyRepo;
import com.LegisTrack.LegisTrack.service.IPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class PartyService implements IPartyService {
    private final PartyRepo partyRepo;

    @Autowired
    public PartyService(PartyRepo partyRepo) {
        this.partyRepo = partyRepo;
    }

    /**
     * Ensures that the user has parties.
      * @param userId The ID of the user.
     */
    public void ensureUserHasParties(String userId) {

        List<Party> existing = partyRepo.findByUserId(userId);
        if (!existing.isEmpty()) return;

        List<Party> globalParties = partyRepo.findByUserId("GLOBAL");
        for (Party global : globalParties) {
            Party copy = new Party();
            copy.setName(global.getName());
            copy.setNrOfDeputies(global.getNrOfDeputies());
            copy.setUserId(userId);

            partyRepo.save(copy);
        }
    }

    /**
     * Creates a new Party for the given user.
     */
    @Override
    public PartyDto createPartyByUserId(PartyInputDto partyInputDTO, String userId) {
        Party party = new Party();
        PartyMapper.dtoToDomain(partyInputDTO, party);
        party.setUserId(userId);
        partyRepo.save(party);
        return PartyMapper.domainToDto(party, new PartyDto());
    }

    /**
     * Retrieves a Party by its ID for the given user.
     */
    @Override
    public PartyDto getPartyByIdAndUserId(Long id, String userId) {
        Party party = partyRepo.findById(id)
                .orElseThrow(() -> new BusinessException("Party not found with ID: " + id));
        if (!party.getUserId().equals(userId)) {
            throw new BusinessException("Party does not belong to user: " + userId);
        }
        return PartyMapper.domainToDto(party, new PartyDto());
    }

    /**
     * Retrieves all Parties for the given user.
     */
    @Override
    public List<PartyDto> getAllParties(String userId) {
        List<Party> parties = partyRepo.findByUserId(userId);
        if (parties.isEmpty()) {
            throw new BusinessException("No parties found for user: " + userId);
        }
        List<PartyDto> partyDTOs = new ArrayList<>();
        for (Party party : parties) {
            partyDTOs.add(PartyMapper.domainToDto(party, new PartyDto()));
        }
        return partyDTOs;
    }

    /**
     * Increments the number of deputies for a party.
     */
    @Override
    public PartyDto incrementDeputies(Long partyId, int count, String userId) {
        Party party = partyRepo.findById(partyId)
                .orElseThrow(() -> new BusinessException("Party not found with ID: " + partyId));
        if (!party.getUserId().equals(userId)) {
            throw new BusinessException("Party does not belong to user: " + userId);
        }
        party.setNrOfDeputies(party.getNrOfDeputies() + count);
        partyRepo.save(party);
        return PartyMapper.domainToDto(party, new PartyDto());
    }

    /**
     * Decrements the number of deputies for a party.
     */
    @Override
    public PartyDto decrementDeputies(Long partyId, int count, String userId) {
        Party party = partyRepo.findById(partyId)
                .orElseThrow(() -> new BusinessException("Party not found with ID: " + partyId));
        if (!party.getUserId().equals(userId)) {
            throw new BusinessException("Party does not belong to user: " + userId);
        }
        int newCount = party.getNrOfDeputies() - count;
        if (newCount < 0) {
            throw new BusinessException("The number of deputies cannot be less than 0");
        }
        party.setNrOfDeputies(newCount);
        partyRepo.save(party);
        return PartyMapper.domainToDto(party, new PartyDto());
    }

    /**
     * Updates an existing Party for the given user.
     */

    @Override
    public PartyDto updateParty(Long id, PartyInputDto partyInputDTO, String userId) {
        Party party = partyRepo.findById(id)
                .orElseThrow(() -> new BusinessException("Party not found with ID: " + id));
        if (!party.getUserId().equals(userId)) {
            throw new BusinessException("Party does not belong to user: " + userId);
        }
        PartyMapper.dtoToDomain(partyInputDTO, party);
        partyRepo.save(party);
        return PartyMapper.domainToDto(party, new PartyDto());
    }

    /**
     * Deletes a Party for the given user.
     */
    @Override
    public void deleteParty(Long id, String userId) {
        Party party = partyRepo.findById(id)
                .orElseThrow(() -> new BusinessException("Party not found with ID: " + id));
        if (!party.getUserId().equals(userId)) {
            throw new BusinessException("Party does not belong to user: " + userId);
        }
        partyRepo.delete(party);
    }
}
