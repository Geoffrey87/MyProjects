package Memento.dtos.OutputDto;

import Memento.entities.Visibility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlbumOutputDto {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private Visibility visibility;
    private UserSummaryDto owner;
    private Integer mediaCount;
}

