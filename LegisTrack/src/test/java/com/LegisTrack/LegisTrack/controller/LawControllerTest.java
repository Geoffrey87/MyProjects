package com.LegisTrack.LegisTrack.controller;

import com.LegisTrack.LegisTrack.Dto.LawDto;
import com.LegisTrack.LegisTrack.Dto.LawInputDto;
import com.LegisTrack.LegisTrack.service.ILawService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class LawControllerTest {

    @Mock
    private ILawService lawService;

    @InjectMocks
    private LawController lawController;
    private LawDto lawDto;
    private LawInputDto lawInputDto;
    private final String testUserId = "testUser123";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        lawDto = new LawDto();
        lawDto.setId(1L);
        lawDto.setDescription("Test Law");
        lawDto.setProposingPartyName("Test Party");

        lawInputDto = new LawInputDto();
        lawInputDto.setDescription("Test Law");
        lawInputDto.setProposingPartyId(1L);
    }

    //SUCCESS TESTS

    @Test
    void createLaw_ShouldReturnCreatedLaw() {
        when(lawService.createLaw(any(LawInputDto.class), eq(testUserId)))
                .thenReturn(lawDto);

        ResponseEntity<LawDto> response = lawController.createLaw(testUserId, lawInputDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(lawDto, response.getBody());
        verify(lawService).createLaw(lawInputDto, testUserId);
    }

    @Test
    void getLawById_ShouldReturnLaw() {
        when(lawService.getLawById(1L)).thenReturn(lawDto);

        ResponseEntity<LawDto> response = lawController.getLawById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(lawDto, response.getBody());
        verify(lawService).getLawById(1L);
    }

    @Test
    void getAllLaws_ShouldReturnAllLaws() {
        List<LawDto> expectedLaws = List.of(lawDto);

        when(lawService.getLawsByUserId(testUserId))
                .thenReturn(expectedLaws);

        ResponseEntity<List<LawDto>> response = lawController.getAllLaws(testUserId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(lawService).getLawsByUserId(testUserId);
    }

    @Test
    void getLawsByUserId_ShouldReturnUserLaws() {
        List<LawDto> userLaws = Arrays.asList(lawDto);
        when(lawService.getLawsByUserId(testUserId)).thenReturn(userLaws);

        ResponseEntity<List<LawDto>> response = lawController.getAllLaws(testUserId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(lawService).getLawsByUserId(testUserId);
    }

    //EXCEPTION TESTS

    @Test
    void getLawById_WhenLawNotFound_ShouldReturnNotFound() {
        when(lawService.getLawById(99L))
                .thenThrow(new RuntimeException("Law not found with ID: 99"));

        Exception exception = assertThrows(RuntimeException.class, () ->
                lawController.getLawById(99L)
        );

        assertEquals("Law not found with ID: 99", exception.getMessage());
        verify(lawService).getLawById(99L);
    }

    @Test
    void getAllLaws_WhenNoLawsExist_ShouldReturnNotFound() {
        when(lawService.getLawsByUserId(testUserId)).thenThrow(new RuntimeException("No parties found for user: " + testUserId));

        Exception exception = assertThrows(RuntimeException.class,
                () -> lawController.getAllLaws(testUserId)
        );

        assertEquals("No parties found for user: " + testUserId, exception.getMessage());
        verify(lawService).getLawsByUserId(testUserId);
    }


}
