package Memento.dtos.InputDto;

import Memento.entities.Visibility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateDto {

    @Size(max = 20000, message = "Content is too long")
    private String content; // optional if post has media

    @NotNull(message = "Visibility is required")
    private Visibility visibility;

    private List<Long> tagIds;   // optional
    private List<Long> mediaIds; // optional, link to existing uploaded media
}

