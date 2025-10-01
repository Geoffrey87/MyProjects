package Memento.dtos.OutputDto;

import Memento.entities.enums.CommentTargetType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentOutputDto {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private UserSummaryDto author;
    private CommentTargetType targetType;
    private Long targetId;
}

