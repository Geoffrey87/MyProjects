package com.LegisTrack.LegisTrack.service.impl;

import com.LegisTrack.LegisTrack.Dto.LawDto;
import com.LegisTrack.LegisTrack.Dto.LawInputDto;
import com.LegisTrack.LegisTrack.entity.Law;
import com.LegisTrack.LegisTrack.entity.Party;
import com.LegisTrack.LegisTrack.exception.BusinessException;
import com.LegisTrack.LegisTrack.mapper.LawMapper;
import com.LegisTrack.LegisTrack.repository.LawRepo;
import com.LegisTrack.LegisTrack.repository.PartyRepo;
import com.LegisTrack.LegisTrack.service.ILawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of ILawService to handle law-related operations.
 */
@Service
public class LawService implements ILawService {

    private final LawRepo lawRepo;
    private final PartyRepo partyRepo;

    @Autowired
    public LawService(LawRepo lawRepo, PartyRepo partyRepo) {
        this.lawRepo = lawRepo;
        this.partyRepo = partyRepo;
    }

    /**
     * Creates a new Law entity from the input DTO, saves it, and returns a DTO representation.
     *
     * @param lawInputDto The DTO containing the law's data.
     * @return The created Law as a LawDto.
     */
    @Override
    public LawDto createLaw(LawInputDto lawInputDto, String userId) {
        // Find the party by ID and validate ownership
        Party party = partyRepo.findById(lawInputDto.getProposingPartyId())
                .orElseThrow(() -> new BusinessException("Party not found"));

        if (!party.getUserId().equals(userId)) {
            throw new BusinessException("You can only propose laws for your own parties.");
        }

        // Create the new law
        Law newLaw = new Law();
        LawMapper.dtoToDomain(lawInputDto, newLaw);
        newLaw.setProposingParty(party);

        // Save the law
        lawRepo.save(newLaw);

        // Return the response
        return LawMapper.domainToDto(newLaw, new LawDto());
    }

    /**
     * Retrieves a law by its ID.
     *
     * @param id The ID of the law.
     * @return The Law as a LawDto.
     */
    @Override
    public LawDto getLawById(Long id) {
        Law law = lawRepo.findById(id)
                .orElseThrow(() -> new BusinessException("Law not found with ID: " + id));
        return LawMapper.domainToDto(law, new LawDto());
    }

    /**
     * Retrieves all laws and converts them to a list of DTOs.
     *
     * @return A list of LawDto.
     */
    @Override
    public List<LawDto> getAllLaws() {
        List<Law> laws = lawRepo.findAll();
        if (laws.isEmpty()) {
            throw new BusinessException("No laws found.");
        }
        List<LawDto> lawDtos = new ArrayList<>();
        for (Law law : laws) {
            lawDtos.add(LawMapper.domainToDto(law, new LawDto()));
        }
        return lawDtos;
    }

    @Override
    public List<LawDto> getLawsByUserId(String userId) {
        List<Party> parties = partyRepo.findByUserId(userId);
        if (parties.isEmpty()) {
            throw new BusinessException("No parties found for user: " + userId);
        }
        List<LawDto> lawDtos = new ArrayList<>();

        for (Party party : parties) {
            for (Law law : party.getProposedLaws()) {
                lawDtos.add(LawMapper.domainToDto(law, new LawDto()));
            }
        }

        return lawDtos;
    }
}
