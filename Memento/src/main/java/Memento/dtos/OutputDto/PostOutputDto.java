package Memento.dtos.OutputDto;

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
public class PostOutputDto {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Visibility visibility;
    private UserSummaryDto author;
    private List<TagOutputDto> tags;
    private List<MediaOutputDto> media;
}

