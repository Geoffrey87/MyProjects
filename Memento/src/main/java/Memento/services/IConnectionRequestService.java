package Memento.services;

import Memento.dtos.InputDto.ConnectionRequestCreateDto;
import Memento.dtos.InputDto.ConnectionRequestRespondDto;
import Memento.dtos.OutputDto.ConnectionRequestOutputDto;
import Memento.entities.User;

import java.util.List;

public interface IConnectionRequestService {

    /**
     * Send a new connection request from one user to another.
     */
    ConnectionRequestOutputDto sendRequest(User sender, ConnectionRequestCreateDto dto);

    /**
     * Respond (ACCEPT / REJECT) to a received connection request.
     */
    ConnectionRequestOutputDto respondRequest(User receiver, ConnectionRequestRespondDto dto);

    /**
     * Cancel a request sent by the authenticated user (if still pending).
     */
    void cancelRequest(Long requestId, User sender);

    /**
     * Get all connection requests received by the authenticated user.
     */
    List<ConnectionRequestOutputDto> getReceivedRequests(User receiver);

    /**
     * Get all connection requests sent by the authenticated user.
     */
    List<ConnectionRequestOutputDto> getSentRequests(User sender);
}

