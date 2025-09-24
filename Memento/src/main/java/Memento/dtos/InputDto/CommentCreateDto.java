package Memento.dtos.InputDto;

import Memento.entities.CommentTargetType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateDto {

    @NotBlank(message = "Content is required")
    @Size(max = 10000, message = "Content too long")
    private String content;

    @NotNull(message = "Target type is required")
    private CommentTargetType targetType;

    @NotNull(message = "Target id is required")
    @Positive(message = "Target id must be positive")
    private Long targetId;
}

