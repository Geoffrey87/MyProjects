package Memento.dtos.InputDto;

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
public class ConnectionRequestCreateDto {

    @NotNull(message = "Receiver id is required")
    @Positive(message = "Receiver id must be positive")
    private Long receiverId;
}

