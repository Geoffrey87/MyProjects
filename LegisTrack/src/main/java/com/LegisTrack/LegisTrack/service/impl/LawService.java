package com.LegisTrack.LegisTrack.service.impl;

import com.LegisTrack.LegisTrack.Dto.LawDto;
import com.LegisTrack.LegisTrack.Dto.LawInputDto;
import com.LegisTrack.LegisTrack.entity.Law;
import com.LegisTrack.LegisTrack.mapper.LawMapper;
import com.LegisTrack.LegisTrack.repository.LawRepo;
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

    @Autowired
    public LawService(LawRepo lawRepo) {
        this.lawRepo = lawRepo;
    }

    /**
     * Creates a new Law entity from the input DTO, saves it, and returns a DTO representation.
     *
     * @param lawInputDto The DTO containing the law's data.
     * @return The created Law as a LawDto.
     */
    @Override
    public LawDto createLaw(LawInputDto lawInputDto) {
        Law law = new Law();
        // Map the input DTO to the Law entity.
        LawMapper.dtoToDomain(lawInputDto, law);
        // Save the new law entity.
        law = lawRepo.save(law);
        // Convert the saved Law entity to an output DTO.
        return LawMapper.domainToDto(law, new LawDto());
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
                .orElseThrow(() -> new RuntimeException("Law not found with ID: " + id));
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
        List<LawDto> lawDtos = new ArrayList<>();
        for (Law law : laws) {
            lawDtos.add(LawMapper.domainToDto(law, new LawDto()));
        }
        return lawDtos;
    }
}
