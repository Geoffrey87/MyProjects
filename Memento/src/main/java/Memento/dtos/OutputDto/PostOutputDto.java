package Memento.dtos.OutputDto;

import Memento.entities.Visibility;
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
    private Visibility visibility;
    private UserSummaryDto author;
    private List<TagOutputDto> tags;
}

