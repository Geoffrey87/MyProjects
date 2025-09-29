package Memento.dtos.InputDto;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {

    @Size(max = 100, message = "Name must be at most 100 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name must contain only letters and spaces")
    private String name;

    @Size(max = 255, message = "Avatar URL must be at most 255 characters")
    private String avatarUrl;

    @Size(max = 150, message = "Location must be at most 150 characters")
    private String location;

    private Integer age;
}

