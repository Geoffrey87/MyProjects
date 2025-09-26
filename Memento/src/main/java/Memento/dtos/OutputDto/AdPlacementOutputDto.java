package Memento.dtos.OutputDto;

import Memento.entities.AdStatus;
import Memento.entities.Visibility;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdPlacementOutputDto {
    private Long id;
    private String title;
    private String targetUrl;
    private String mediaUrl;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String advertiserName;
    private Long categoryId;
    private Long tagId;
    private Visibility visibility;
    private AdStatus status;
}


