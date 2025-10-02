package Memento.mapper;

import Memento.dtos.InputDto.ConnectionRequestRespondDto;
import Memento.dtos.OutputDto.ConnectionRequestOutputDto;
import Memento.dtos.OutputDto.UserSummaryDto;
import Memento.entities.ConnectionRequest;
import Memento.entities.User;

import java.util.Objects;

public class ConnectionRequestMapper {

    public static ConnectionRequestOutputDto domainToDto(ConnectionRequest request, ConnectionRequestOutputDto dto,
                                                         UserSummaryDto senderDto, UserSummaryDto receiverDto) {
        Objects.requireNonNull(request, "request must not be null");
        Objects.requireNonNull(dto, "dto must not be null");

        dto.setId(request.getId());
        dto.setStatus(request.getStatus());
        dto.setSentAt(request.getSentAt());
        dto.setRespondedAt(request.getRespondedAt());

        if (request.getSender() != null && senderDto != null) {
            senderDto.setId(request.getSender().getId());
            senderDto.setName(request.getSender().getName());
            senderDto.setAvatarUrl(request.getSender().getAvatarUrl());
            dto.setSender(senderDto);
        }

        if (request.getReceiver() != null && receiverDto != null) {
            receiverDto.setId(request.getReceiver().getId());
            receiverDto.setName(request.getReceiver().getName());
            receiverDto.setAvatarUrl(request.getReceiver().getAvatarUrl());
            dto.setReceiver(receiverDto);
        }

        return dto;
    }

    public static ConnectionRequest applyRespondDto(ConnectionRequestRespondDto dto, ConnectionRequest request) {
        Objects.requireNonNull(dto, "dto must not be null");
        Objects.requireNonNull(request, "request must not be null");

        request.setStatus(dto.getStatus());
        request.setRespondedAt(java.time.LocalDateTime.now());

        return request;
    }
}



