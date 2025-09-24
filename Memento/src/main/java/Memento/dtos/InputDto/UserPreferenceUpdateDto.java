package Memento.dtos.InputDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPreferenceUpdateDto {

    @NotNull(message = "preferredTagIds is required")
    @NotEmpty(message = "preferredTagIds cannot be empty")
    private List<@Positive(message = "Tag id must be positive") Long> preferredTagIds;
}

