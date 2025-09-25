package Memento.mapper;

import Memento.dtos.OutputDto.AuditLogOutputDto;
import Memento.dtos.OutputDto.UserSummaryDto;
import Memento.entities.AuditLog;

public class AuditLogMapper {

    public static AuditLogOutputDto domainToDto(AuditLog log, AuditLogOutputDto dto) {
        dto.setId(log.getId());
        dto.setAction(log.getAction()); // AuditAction enum
        dto.setTimestamp(log.getTimestamp());
        dto.setDetails(log.getDetails());

        if (log.getPerformedBy() != null && dto.getPerformedBy() != null) {
            dto.getPerformedBy().setId(log.getPerformedBy().getId());
            dto.getPerformedBy().setName(log.getPerformedBy().getName());
            dto.getPerformedBy().setAvatarUrl(log.getPerformedBy().getAvatarUrl());
        }

        return dto;
    }
}

