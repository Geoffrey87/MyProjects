package Memento.dtos.OutputDto;

import Memento.entities.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionRequestOutputDto {
    private Long id;
    private RequestStatus status;
    private LocalDateTime sentAt;
    private LocalDateTime respondedAt;
    private UserSummaryDto sender;
    private UserSummaryDto receiver;
}

