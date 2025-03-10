package com.LegisTrack.LegisTrack.service;

import com.LegisTrack.LegisTrack.Dto.LawDto;
import com.LegisTrack.LegisTrack.Dto.LawInputDto;
import java.util.List;

/**
 * Service interface for handling operations related to Law.
 */
public interface ILawService {

    /**
     * Creates a new law from the provided input DTO.
     *
     * @param lawInputDto The DTO containing input data for the law.
     * @return The created law as a LawDto.
     */
    LawDto createLaw(LawInputDto lawInputDto);

    /**
     * Retrieves a law by its ID.
     *
     * @param id The ID of the law.
     * @return The law as a LawDto.
     */
    LawDto getLawById(Long id);

    /**
     * Retrieves all laws.
     *
     * @return A list of LawDto.
     */
    List<LawDto> getAllLaws();
}
