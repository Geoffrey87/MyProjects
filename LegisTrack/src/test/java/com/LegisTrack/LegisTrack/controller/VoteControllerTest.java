package com.LegisTrack.LegisTrack.controller;

import com.LegisTrack.LegisTrack.Dto.VoteCountDto;
import com.LegisTrack.LegisTrack.Dto.VoteDto;
import com.LegisTrack.LegisTrack.Dto.VoteInputDto;
import com.LegisTrack.LegisTrack.service.IVoteService;
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
import static org.mockito.Mockito.*;

class VoteControllerTest {

    @Mock
    private IVoteService voteService;

    @InjectMocks
    private VoteController voteController;

    private VoteDto voteDto;
    private VoteInputDto voteInputDto;
    private VoteCountDto voteCountDto;

    private VoteDto voteDtoInFavor;
    private VoteDto voteDtoAgainst;
    private VoteDto voteDtoAbstention;

    private VoteInputDto voteInputDtoInFavor;
    private VoteInputDto voteInputDtoAgainst;
    private VoteInputDto voteInputDtoAbstention;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // IN_FAVOR
        voteDtoInFavor = new VoteDto();
        voteDtoInFavor.setDescription("Test Vote - In Favor");
        voteDtoInFavor.setVoteType("IN_FAVOR");

        voteInputDtoInFavor = new VoteInputDto();
        voteInputDtoInFavor.setVoteType("IN_FAVOR");

        // AGAINST
        voteDtoAgainst = new VoteDto();
        voteDtoAgainst.setDescription("Test Vote - Against");
        voteDtoAgainst.setVoteType("AGAINST");

        voteInputDtoAgainst = new VoteInputDto();
        voteInputDtoAgainst.setVoteType("AGAINST");

        // ABSTENTION
        voteDtoAbstention = new VoteDto();
        voteDtoAbstention.setDescription("Test Vote - Abstention");
        voteDtoAbstention.setVoteType("ABSTENTION");

        voteInputDtoAbstention = new VoteInputDto();
        voteInputDtoAbstention.setVoteType("ABSTENTION");

        // Example for vote counts
        voteCountDto = new VoteCountDto();
        voteCountDto.setInFavor(3);
        voteCountDto.setAgainst(2);
        voteCountDto.setAbstention(1);
    }


    @Test
    void createVote_ShouldReturnCreatedVote() {
        when(voteService.createVote(any(VoteInputDto.class))).thenReturn(voteDtoInFavor);

        ResponseEntity<VoteDto> response = voteController.createVote(voteInputDtoInFavor);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("IN_FAVOR", response.getBody().getVoteType());
        verify(voteService).createVote(voteInputDtoInFavor);
    }


    @Test
    void getVoteById_ShouldReturnVote() {
        // Ensure the voteDtoInFavor has the expected id
        voteDtoInFavor.setId(1L);
        when(voteService.getVoteById(1L)).thenReturn(voteDtoInFavor);

        ResponseEntity<VoteDto> response = voteController.getVoteById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
        verify(voteService).getVoteById(1L);
    }


    @Test
    void getAllVotes_ShouldReturnAllVotes() {
        List<VoteDto> votes = Arrays.asList(voteDtoInFavor);
        when(voteService.getAllVotes()).thenReturn(votes);

        ResponseEntity<List<VoteDto>> response = voteController.getAllVotes();

        assertEquals(1, response.getBody().size());
        assertEquals("IN_FAVOR", response.getBody().getFirst().getVoteType());
        verify(voteService).getAllVotes();
    }


    @Test
    void getVoteCountsByLaw_ShouldReturnCounts() {
        when(voteService.getVoteCountsByLaw(1L)).thenReturn(voteCountDto);

        ResponseEntity<VoteCountDto> response = voteController.getVoteCountsByLaw(1L);

        assertEquals(3, response.getBody().getInFavor());
        assertEquals(2, response.getBody().getAgainst());
        assertEquals(1, response.getBody().getAbstention());
        verify(voteService).getVoteCountsByLaw(1L);
    }

    @Test
    void getVoteById_WhenVoteNotFound_ThrowsException() {
        when(voteService.getVoteById(99L))
                .thenThrow(new RuntimeException("Vote not found with ID: 99"));

        Exception exception = assertThrows(RuntimeException.class,
                () -> voteController.getVoteById(99L)
        );

        assertEquals("Vote not found with ID: 99", exception.getMessage());
    }

    @Test
    void getVoteCountsByLaw_WhenLawNotFound_ThrowsException() {
        when(voteService.getVoteCountsByLaw(99L))
                .thenThrow(new RuntimeException("Law not found with ID: 99"));

        Exception exception = assertThrows(RuntimeException.class,
                () -> voteController.getVoteCountsByLaw(99L)
        );

        assertEquals("Law not found with ID: 99", exception.getMessage());
    }
}