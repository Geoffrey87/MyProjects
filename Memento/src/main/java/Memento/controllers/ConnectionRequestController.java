package Memento.controllers;

import Memento.dtos.InputDto.ConnectionRequestCreateDto;
import Memento.dtos.InputDto.ConnectionRequestRespondDto;
import Memento.dtos.OutputDto.ConnectionRequestOutputDto;
import Memento.security.SecurityUserDetails;
import Memento.services.IConnectionRequestService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/connections")
public class ConnectionRequestController {

    private final IConnectionRequestService connectionRequestService;

    public ConnectionRequestController(IConnectionRequestService connectionRequestService) {
        this.connectionRequestService = connectionRequestService;
    }

    // ---------------------------
    // SEND CONNECTION REQUEST
    // ---------------------------
    @PostMapping("/send")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ConnectionRequestOutputDto> sendRequest(
            @Valid @RequestBody ConnectionRequestCreateDto dto,
            @AuthenticationPrincipal SecurityUserDetails userDetails
    ) {
        ConnectionRequestOutputDto created = connectionRequestService.sendRequest(userDetails.getUser(), dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ---------------------------
    // RESPOND TO CONNECTION REQUEST
    // ---------------------------
    @PutMapping("/respond")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ConnectionRequestOutputDto> respondRequest(
            @Valid @RequestBody ConnectionRequestRespondDto dto,
            @AuthenticationPrincipal SecurityUserDetails userDetails
    ) {
        ConnectionRequestOutputDto updated = connectionRequestService.respondRequest(userDetails.getUser(), dto);
        return ResponseEntity.ok(updated);
    }

    // ---------------------------
    // CANCEL SENT REQUEST
    // ---------------------------
    @DeleteMapping("/{id}/cancel")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> cancelRequest(
            @PathVariable Long id,
            @AuthenticationPrincipal SecurityUserDetails userDetails
    ) {
        connectionRequestService.cancelRequest(id, userDetails.getUser());
        return ResponseEntity.noContent().build();
    }

    // ---------------------------
    // LIST RECEIVED REQUESTS
    // ---------------------------
    @GetMapping("/received")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ConnectionRequestOutputDto>> getReceivedRequests(
            @AuthenticationPrincipal SecurityUserDetails userDetails
    ) {
        List<ConnectionRequestOutputDto> requests = connectionRequestService.getReceivedRequests(userDetails.getUser());
        return ResponseEntity.ok(requests);
    }

    // ---------------------------
    // LIST SENT REQUESTS
    // ---------------------------
    @GetMapping("/sent")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ConnectionRequestOutputDto>> getSentRequests(
            @AuthenticationPrincipal SecurityUserDetails userDetails
    ) {
        List<ConnectionRequestOutputDto> requests = connectionRequestService.getSentRequests(userDetails.getUser());
        return ResponseEntity.ok(requests);
    }
}

