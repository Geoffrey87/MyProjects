package Memento.dtos.InputDto;

import Memento.entities.enums.MediaType;
import Memento.entities.enums.Visibility;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MediaCreateDto {

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must be at most 255 characters")
    private String title;

    @NotNull(message = "Type is required")
    private MediaType type; // PHOTO or VIDEO

    @NotBlank(message = "URL is required")
    @Size(max = 2000, message = "URL must be at most 2000 characters")
    @Pattern(
            regexp = "^(https?://).+",
            message = "URL must be valid and start with http or https"
    )
    private String url;

    @NotNull(message = "Visibility is required")
    private Visibility visibility; // FRIENDS, FAMILY, BOTH, PUBLIC

    private Long albumId; // optional, null if media is not part of an album
}

