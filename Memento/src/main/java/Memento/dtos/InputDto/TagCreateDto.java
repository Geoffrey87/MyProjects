package Memento.dtos.InputDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagCreateDto {

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name max 100 characters")
    @Pattern(regexp = "^[\\p{L} ]+$", message = "Name must contain only letters and spaces")
    private String name;

    @NotNull(message = "Category id is required")
    @Positive(message = "Category id must be positive")
    private Long categoryId;
}

