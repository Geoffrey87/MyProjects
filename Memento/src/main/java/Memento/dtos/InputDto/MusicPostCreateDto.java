package Memento.dtos.InputDto;

import Memento.entities.Visibility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MusicPostCreateDto {

    @NotBlank(message = "Platform is required")
    @Size(max = 100, message = "Platform max 100 characters")
    private String platform;

    @NotBlank(message = "Track URL is required")
    @Pattern(regexp = "^(http|https)://.*$", message = "Must be a valid URL")
    @Size(max = 2000, message = "Track URL max 2000 characters")
    private String trackUrl;

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title max 255 characters")
    private String title;

    @NotBlank(message = "Artist is required")
    @Size(max = 255, message = "Artist max 255 characters")
    private String artist;

    @Size(max = 2000, message = "Cover URL max 2000 characters")
    private String coverUrl;

    @NotNull(message = "Visibility is required")
    private Visibility visibility;
}

