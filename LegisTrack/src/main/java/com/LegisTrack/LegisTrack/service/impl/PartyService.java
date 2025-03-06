package com.LegisTrack.LegisTrack.service.impl;

import com.LegisTrack.LegisTrack.Dto.PartyDto;
import com.LegisTrack.LegisTrack.Dto.PartyInputDto;
import com.LegisTrack.LegisTrack.entity.Party;
import com.LegisTrack.LegisTrack.mapper.PartyMapper;
import com.LegisTrack.LegisTrack.repository.PartyRepo;
import com.LegisTrack.LegisTrack.service.IPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PartyServiceService implements IPartyService {
    private final PartyRepo partyRepo;

    @Autowired
    public PartyServiceService(PartyRepo partyRepo) {
        this.partyRepo = partyRepo;
    }

    @Override
    public PartyDto createParty(PartyInputDto partyInputDTO) {
        Party party = new Party();
        PartyMapper.dtoToDomain(partyInputDTO, party);
        partyRepo.save(party);
        return PartyMapper.domainToDto(party, new PartyDto());
    }
    public PartyDto incrementDeputies(Long partyId, int count) {
        Party party = partyRepo.findById(partyId)
                .orElseThrow(() -> new RuntimeException("Party not found with ID: " + partyId));

        party.setNrOfDeputies(party.getNrOfDeputies() + count);
        partyRepo.save(party);
        return PartyMapper.domainToDto(party, new PartyDto());
    }

    public PartyDto decrementDeputies(Long partyId, int count) {
        Party party = partyRepo.findById(partyId)
                .orElseThrow(() -> new RuntimeException("Party not found with ID: " + partyId));

        int newCount = party.getNrOfDeputies() - count;
        if (newCount < 0) {
            throw new RuntimeException("The number of deputies cannot be less than 0");
        }
        party.setNrOfDeputies(newCount);
        partyRepo.save(party);
        return PartyMapper.domainToDto(party, new PartyDto());
    }

    @Override
    public PartyDto updateParty(Long id, PartyInputDto partyInputDTO) {
        // Fetch the existing Party from the DB
        Party party = partyRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Party not found with ID: " + id));

        // Update the Party entity fields from the input DTO
        PartyMapper.dtoToDomain(partyInputDTO, party);

        // Save the updated Party entity
        party = partyRepo.save(party);

        // Return the updated Party as a DTO
        return PartyMapper.domainToDto(party, new PartyDto());
    }


    @Override
    public PartyDto getPartyById(Long id) {
        // Find the Party by ID
        Party party = partyRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Party not found with ID: " + id));

        // Map to DTO
        return PartyMapper.domainToDto(party, new PartyDto());
    }

    @Override
    public List<PartyDto> getAllParties() {
        // Retrieve all parties from the DB
        List<Party> parties = partyRepo.findAll();

        if (parties.isEmpty()) {
            throw new RuntimeException("No parties found");
        }
        else {
            // Convert each Party entity to a PartyDTO using a loop
            List<PartyDto> partyDTOs = new ArrayList<>();
            for (Party party : parties) {
                PartyDto dto = PartyMapper.domainToDto(party, new PartyDto());
                partyDTOs.add(dto);
            }
            return partyDTOs;
        }
    }


    @Override
    public void deleteParty(Long id) {
        // Find the Party by ID
        Party party = partyRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Party not found with ID: " + id));

        // Delete the Party
        partyRepo.delete(party);
    }
}
