package Memento.dtos.InputDto;

import Memento.entities.enums.StoryType;
import Memento.entities.enums.Visibility;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoryCreateDto {

    @NotNull(message = "Type is required")
    private StoryType type;

    @NotBlank(message = "URL is required")
    @Size(max = 2000, message = "URL max 2000 characters")
    private String url;

    @NotNull(message = "SizeMB is required")
    @Positive(message = "SizeMB must be positive")
    private Double sizeMB;

    @NotNull(message = "ExpiresAt is required")
    @Future(message = "ExpiresAt must be in the future")
    private LocalDateTime expiresAt;

    @NotNull(message = "Visibility is required")
    private Visibility visibility;
}

