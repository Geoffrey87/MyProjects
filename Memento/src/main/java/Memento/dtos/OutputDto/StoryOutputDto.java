package Memento.dtos.OutputDto;

import Memento.entities.enums.StoryType;
import Memento.entities.enums.Visibility;
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
public class StoryOutputDto {
    private Long id;
    private StoryType type;
    private String url;
    private Double sizeMB;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private Visibility visibility;
    private UserSummaryDto author;
    private List<TagOutputDto> tags;
}

