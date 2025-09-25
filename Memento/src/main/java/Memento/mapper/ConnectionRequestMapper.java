package Memento.mapper;

import Memento.dtos.InputDto.ConnectionRequestRespondDto;
import Memento.dtos.OutputDto.ConnectionRequestOutputDto;
import Memento.dtos.OutputDto.UserSummaryDto;
import Memento.entities.ConnectionRequest;
import Memento.entities.User;

import java.util.Objects;

public class ConnectionRequestMapper {

    public static void domainToDto(ConnectionRequest request, ConnectionRequestOutputDto dto) {
        Objects.requireNonNull(request, "request must not be null");
        Objects.requireNonNull(dto, "dto must not be null");

        dto.setId(request.getId());
        dto.setStatus(request.getStatus());
        dto.setSentAt(request.getSentAt());
        dto.setRespondedAt(request.getRespondedAt());

        // Sender
        if (request.getSender() != null && dto.getSender() != null) {
            User sender = request.getSender();
            UserSummaryDto senderDto = dto.getSender();
            senderDto.setId(sender.getId());
            senderDto.setName(sender.getName());
            senderDto.setAvatarUrl(sender.getAvatarUrl());
        }

        // Receiver
        if (request.getReceiver() != null && dto.getReceiver() != null) {
            User receiver = request.getReceiver();
            UserSummaryDto receiverDto = dto.getReceiver();
            receiverDto.setId(receiver.getId());
            receiverDto.setName(receiver.getName());
            receiverDto.setAvatarUrl(receiver.getAvatarUrl());
        }
    }

    public static void applyRespondDto(ConnectionRequestRespondDto dto, ConnectionRequest request) {
        Objects.requireNonNull(dto, "dto must not be null");
        Objects.requireNonNull(request, "request must not be null");

        request.setStatus(dto.getStatus());

    }
}
