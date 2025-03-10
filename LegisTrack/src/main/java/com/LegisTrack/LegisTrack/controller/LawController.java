package com.LegisTrack.LegisTrack.controller;

import com.LegisTrack.LegisTrack.Dto.LawDto;
import com.LegisTrack.LegisTrack.Dto.LawInputDto;
import com.LegisTrack.LegisTrack.service.ILawService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/laws")
public class LawController {

    private final ILawService lawService;

    public LawController(ILawService lawService) {
        this.lawService = lawService;
    }

    /**
     * Creates a new law.
     * @param lawInputDto The input DTO containing the law details.
     * @return The created law as a LawDto.
     */
    @PostMapping
    public ResponseEntity<LawDto> createLaw(@RequestBody LawInputDto lawInputDto) {
        LawDto createdLaw = lawService.createLaw(lawInputDto);
        return new ResponseEntity<>(createdLaw, HttpStatus.CREATED);
    }

    /**
     * Retrieves a law by its ID.
     * @param id The ID of the law.
     * @return The law as a LawDto.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LawDto> getLawById(@PathVariable Long id) {
        LawDto lawDto = lawService.getLawById(id);
        return ResponseEntity.ok(lawDto);
    }

    /**
     * Retrieves all laws.
     * @return A list of LawDto.
     */
    @GetMapping
    public ResponseEntity<List<LawDto>> getAllLaws() {
        List<LawDto> lawDtos = lawService.getAllLaws();
        return ResponseEntity.ok(lawDtos);
    }
}
