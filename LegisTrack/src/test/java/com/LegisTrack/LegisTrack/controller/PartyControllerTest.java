package com.LegisTrack.LegisTrack.contoller;

import com.LegisTrack.LegisTrack.Dto.PartyDto;
import com.LegisTrack.LegisTrack.Dto.PartyInputDto;
import com.LegisTrack.LegisTrack.controller.PartyController;
import com.LegisTrack.LegisTrack.service.IPartyService;
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

class PartyControllerTest {

    @Mock
    private IPartyService partyService;

    @InjectMocks
    private PartyController partyController;
    private PartyDto partyDto;
    private PartyInputDto partyInputDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        partyDto = new PartyDto();
        partyDto.setId(1L);
        partyDto.setName("Test Party");

        partyInputDto = new PartyInputDto();
        partyInputDto.setName("Test Party");
    }

    @Test
    void createParty_ShouldReturnCreatedParty() {
        when(partyService.createParty(any(PartyInputDto.class))).thenReturn(partyDto);

        ResponseEntity<PartyDto> response = partyController.createParty(partyInputDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(partyDto, response.getBody());
        verify(partyService).createParty(partyInputDto);
    }

    @Test
    void getPartyById_ShouldReturnParty() {
        when(partyService.getPartyById(1L)).thenReturn(partyDto);

        ResponseEntity<PartyDto> response = partyController.getPartyById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(partyDto, response.getBody());
        verify(partyService).getPartyById(1L);
    }

    @Test
    void getAllParties_ShouldReturnAllParties() {
        List<PartyDto> parties = Arrays.asList(partyDto);
        when(partyService.getAllParties()).thenReturn(parties);

        ResponseEntity<List<PartyDto>> response = partyController.getAllParties();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(partyService).getAllParties();
    }

    @Test
    void incrementDeputies_ShouldUpdateDeputies() {
        when(partyService.incrementDeputies(eq(1L), eq(5))).thenReturn(partyDto);

        ResponseEntity<PartyDto> response = partyController.incrementDeputies(1L, 5);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(partyDto, response.getBody());
        verify(partyService).incrementDeputies(1L, 5);
    }

    @Test
    void decrementDeputies_ShouldUpdateDeputies() {
        when(partyService.decrementDeputies(eq(1L), eq(3))).thenReturn(partyDto);

        ResponseEntity<PartyDto> response = partyController.decrementDeputies(1L, 3);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(partyDto, response.getBody());
        verify(partyService).decrementDeputies(1L, 3);
    }

    @Test
    void updateParty_ShouldReturnUpdatedParty() {
        when(partyService.updateParty(eq(1L), any(PartyInputDto.class))).thenReturn(partyDto);

        ResponseEntity<PartyDto> response = partyController.updateParty(1L, partyInputDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(partyDto, response.getBody());
        verify(partyService).updateParty(1L, partyInputDto);
    }

    @Test
    void deleteParty_ShouldReturnNoContent() {
        doNothing().when(partyService).deleteParty(1L);

        ResponseEntity<Void> response = partyController.deleteParty(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(partyService).deleteParty(1L);
    }

    @Test
    void getPartyById_WhenPartyNotFound_ThrowsException() {
        when(partyService.getPartyById(1L))
                .thenThrow(new RuntimeException("Party not found with ID: 1"));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                partyController.getPartyById(1L)
        );

        assertEquals("Party not found with ID: 1", exception.getMessage());
        verify(partyService).getPartyById(1L);
    }

    @Test
    void incrementDeputies_WhenPartyNotFound_ThrowsException() {
        when(partyService.incrementDeputies(1L, 5))
                .thenThrow(new RuntimeException("Party not found with ID: 1"));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                partyController.incrementDeputies(1L, 5)
        );

        assertEquals("Party not found with ID: 1", exception.getMessage());
        verify(partyService).incrementDeputies(1L, 5);
    }

    @Test
    void decrementDeputies_WhenPartyNotFound_ThrowsException() {
        when(partyService.decrementDeputies(1L, 3))
                .thenThrow(new RuntimeException("Party not found with ID: 1"));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                partyController.decrementDeputies(1L, 3)
        );

        assertEquals("Party not found with ID: 1", exception.getMessage());
        verify(partyService).decrementDeputies(1L, 3);
    }

    @Test
    void decrementDeputies_WhenResultIsNegative_ThrowsException() {
        when(partyService.decrementDeputies(1L, 5))
                .thenThrow(new RuntimeException("The number of deputies cannot be less than 0"));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                partyController.decrementDeputies(1L, 5)
        );

        assertEquals("The number of deputies cannot be less than 0", exception.getMessage());
        verify(partyService).decrementDeputies(1L, 5);
    }

    @Test
    void updateParty_WhenPartyNotFound_ThrowsException() {
        when(partyService.updateParty(eq(1L), any(PartyInputDto.class)))
                .thenThrow(new RuntimeException("Party not found with ID: 1"));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                partyController.updateParty(1L, partyInputDto)
        );

        assertEquals("Party not found with ID: 1", exception.getMessage());
        verify(partyService).updateParty(1L, partyInputDto);
    }

    @Test
    void deleteParty_WhenPartyNotFound_ThrowsException() {
        doThrow(new RuntimeException("Party not found with ID: 1"))
                .when(partyService).deleteParty(1L);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                partyController.deleteParty(1L)
        );

        assertEquals("Party not found with ID: 1", exception.getMessage());
        verify(partyService).deleteParty(1L);
    }

    @Test
    void getAllParties_WhenNoPartiesExist_ThrowsException() {
        when(partyService.getAllParties())
                .thenThrow(new RuntimeException("No parties found"));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> partyController.getAllParties()
        );

        assertEquals("No parties found", exception.getMessage());
        verify(partyService).getAllParties();
    }
}
