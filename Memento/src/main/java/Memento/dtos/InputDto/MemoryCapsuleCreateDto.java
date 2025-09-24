package Memento.dtos.InputDto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemoryCapsuleCreateDto {

    @NotNull(message = "UnlockAt is required")
    @Future(message = "UnlockAt must be in the future")
    private LocalDateTime unlockAt;

    private Boolean isPublished = false;

    // Um e apenas um desses deve ser preenchido; regra validada no serviço
    @Positive(message = "Post id must be positive")
    private Long postId;

    @Positive(message = "Media id must be positive")
    private Long mediaId;

    @Positive(message = "Story id must be positive")
    private Long storyId;
}

