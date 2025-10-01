package Memento.dtos.InputDto;

import Memento.entities.enums.Visibility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlbumCreateDto {

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title max 255 characters")
    private String title;

    @Size(max = 10000, message = "Description too long")
    private String description;

    @NotNull(message = "Visibility is required")
    private Visibility visibility;
}

