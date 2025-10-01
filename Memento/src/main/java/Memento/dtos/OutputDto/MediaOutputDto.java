package Memento.dtos.OutputDto;

import Memento.entities.enums.MediaType;
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
public class MediaOutputDto {
    private Long id;
    private String title;
    private MediaType type;
    private String url;
    private Double sizeMB;
    private LocalDateTime uploadedAt;
    private Visibility visibility;
    private UserSummaryDto owner;
    private Long albumId;
    private List<TagOutputDto> tags;
}

