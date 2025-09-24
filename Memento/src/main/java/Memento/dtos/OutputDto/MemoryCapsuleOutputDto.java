package Memento.dtos.OutputDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemoryCapsuleOutputDto {
    private Long id;
    private LocalDateTime unlockAt;
    private Boolean isPublished;
    private Long postId;
    private Long mediaId;
    private Long storyId;
}

