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
public class FollowRequestDto {

    @NotNull(message = "Target user id is required")
    @Positive(message = "Target user id must be positive")
    private Long targetUserId;
}

