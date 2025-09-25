package Memento.dtos.OutputDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPreferenceOutputDto {
    private Long id;
    private LocalDateTime createdAt;
    private List<TagOutputDto> preferredTags;
}

