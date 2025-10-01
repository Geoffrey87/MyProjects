package Memento.dtos.OutputDto;

import Memento.entities.enums.AuditAction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for audit log output.
 * The server responds with the audit log ID, timestamp, action performed, details of the action,
 * and the email of the user who performed the action.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogOutputDto {
    private Long id;
    private LocalDateTime timestamp;
    private AuditAction action;
    private String details;
    private UserSummaryDto performedBy;
}
