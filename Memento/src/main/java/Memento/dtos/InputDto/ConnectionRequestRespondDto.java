package Memento.dtos.InputDto;

import Memento.entities.enums.RequestStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionRequestRespondDto {

    @NotNull(message = "Request id is required")
    @Positive(message = "Request id must be positive")
    private Long requestId;

    @NotNull(message = "Status is required")
    private RequestStatus status; // ACCEPTED or REJECTED (service valida)
}

