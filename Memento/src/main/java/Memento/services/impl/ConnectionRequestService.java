package Memento.services.impl;

import Memento.dtos.InputDto.ConnectionRequestCreateDto;
import Memento.dtos.InputDto.ConnectionRequestRespondDto;
import Memento.dtos.OutputDto.ConnectionRequestOutputDto;
import Memento.dtos.OutputDto.UserSummaryDto;
import Memento.entities.ConnectionRequest;
import Memento.entities.User;
import Memento.entities.enums.RequestStatus;
import Memento.exception.ResourceNotFoundException;
import Memento.mapper.ConnectionRequestMapper;
import Memento.repositories.ConnectionRequestRepository;
import Memento.repositories.UserRepository;
import Memento.services.IConnectionRequestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ConnectionRequestService implements IConnectionRequestService {

    private final ConnectionRequestRepository requestRepository;
    private final UserRepository userRepository;

    public ConnectionRequestService(ConnectionRequestRepository requestRepository,
                                    UserRepository userRepository) {
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ConnectionRequestOutputDto sendRequest(User sender, ConnectionRequestCreateDto dto) {
        User receiver = userRepository.findById(dto.getReceiverId())
                .orElseThrow(() -> new ResourceNotFoundException("Receiver not found with id " + dto.getReceiverId()));

        ConnectionRequest request = new ConnectionRequest();
        request.setSender(sender);
        request.setReceiver(receiver);
        request.setStatus(RequestStatus.PENDING);

        ConnectionRequest saved = requestRepository.save(request);

        ConnectionRequestOutputDto output = new ConnectionRequestOutputDto();
        ConnectionRequestMapper.domainToDto(saved, output,
                new UserSummaryDto(),
                new UserSummaryDto());
        return output;
    }

    @Override
    public ConnectionRequestOutputDto respondRequest(User receiver, ConnectionRequestRespondDto dto) {
        ConnectionRequest request = requestRepository.findById(dto.getRequestId())
                .orElseThrow(() -> new ResourceNotFoundException("Request not found with id " + dto.getRequestId()));

        if (!request.getReceiver().getId().equals(receiver.getId())) {
            throw new SecurityException("You cannot respond to someone else’s request");
        }

        ConnectionRequest updated = ConnectionRequestMapper.applyRespondDto(dto, request);
        ConnectionRequest saved = requestRepository.save(updated);

        ConnectionRequestOutputDto output = new ConnectionRequestOutputDto();
        ConnectionRequestMapper.domainToDto(saved, output,
                new UserSummaryDto(),
                new UserSummaryDto());
        return output;
    }

    @Override
    public void cancelRequest(Long requestId, User sender) {
        ConnectionRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found with id " + requestId));

        if (!request.getSender().getId().equals(sender.getId())) {
            throw new SecurityException("You can only cancel your own requests");
        }
        if (request.getStatus() != RequestStatus.PENDING) {
            throw new IllegalStateException("Only pending requests can be cancelled");
        }

        requestRepository.delete(request);
    }

    @Override
    public List<ConnectionRequestOutputDto> getReceivedRequests(User receiver) {
        List<ConnectionRequest> requests = requestRepository.findByReceiver(receiver);
        List<ConnectionRequestOutputDto> result = new ArrayList<>();

        for (ConnectionRequest req : requests) {
            // Restrição 1: evitar duplicados (mesmo sender/receiver ainda pendente)
            boolean alreadyExists = result.stream().anyMatch(r ->
                    r.getSender().getId().equals(req.getSender().getId()) &&
                            r.getReceiver().getId().equals(req.getReceiver().getId()) &&
                            r.getStatus() == RequestStatus.PENDING
            );
            if (alreadyExists) {
                continue; // ignora duplicado
            }

            // Restrição 2: ignorar pedidos inválidos
            if (req.getStatus() == null) {
                continue;
            }

            // Preencher DTO
            ConnectionRequestOutputDto dto = new ConnectionRequestOutputDto();
            ConnectionRequestMapper.domainToDto(req, dto,
                    new UserSummaryDto(),
                    new UserSummaryDto());

            result.add(dto);
        }

        return result;
    }

    @Override
    public List<ConnectionRequestOutputDto> getSentRequests(User sender) {
        List<ConnectionRequest> requests = requestRepository.findBySender(sender);
        List<ConnectionRequestOutputDto> result = new ArrayList<>();

        for (ConnectionRequest req : requests) {
            // Restrição 1: evitar duplicados (mesmo sender/receiver ainda pendente)
            boolean alreadyExists = result.stream().anyMatch(r ->
                    r.getSender().getId().equals(req.getSender().getId()) &&
                            r.getReceiver().getId().equals(req.getReceiver().getId()) &&
                            r.getStatus() == RequestStatus.PENDING
            );
            if (alreadyExists) {
                continue;
            }

            // Restrição 2: ignorar pedidos inválidos
            if (req.getStatus() == null) {
                continue;
            }

            // Preencher DTO
            ConnectionRequestOutputDto dto = new ConnectionRequestOutputDto();
            ConnectionRequestMapper.domainToDto(req, dto,
                    new UserSummaryDto(),
                    new UserSummaryDto());

            result.add(dto);
        }

        return result;
    }

}

