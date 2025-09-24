package Memento.dtos.InputDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileMusicCreateDto {

    @NotBlank(message = "Platform is required")
    @Size(max = 100, message = "Platform max 100 characters")
    private String platform;

    @NotBlank(message = "Track URL is required")
    @Size(max = 2000, message = "Track URL max 2000 characters")
    private String trackUrl;

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title max 255 characters")
    private String title;

    @NotBlank(message = "Artist is required")
    @Size(max = 255, message = "Artist max 255 characters")
    private String artist;

    private Boolean isActive = true;
}

