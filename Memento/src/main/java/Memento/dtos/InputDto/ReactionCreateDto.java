package Memento.dtos.InputDto;

import Memento.entities.ReactionTargetType;
import Memento.entities.ReactionType;
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
public class ReactionCreateDto {

    @NotNull(message = "Reaction type is required")
    private ReactionType type; // HEART, THUMBS_UP, LAUGH, ...

    @NotNull(message = "Target type is required")
    private ReactionTargetType targetType; // POST, MEDIA, MUSIC_POST

    @NotNull(message = "Target id is required")
    @Positive(message = "Target id must be positive")
    private Long targetId;
}