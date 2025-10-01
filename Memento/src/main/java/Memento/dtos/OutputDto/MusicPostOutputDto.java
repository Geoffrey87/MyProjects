package Memento.dtos.OutputDto;

import Memento.entities.enums.Visibility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MusicPostOutputDto {
    private Long id;
    private String platform;
    private String trackUrl;
    private String title;
    private String artist;
    private String coverUrl;
    private LocalDateTime sharedAt;
    private Visibility visibility;
    private UserSummaryDto author;
}

