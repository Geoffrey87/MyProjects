package Memento.dtos.OutputDto;

import Memento.entities.enums.ReactionTargetType;
import Memento.entities.enums.ReactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReactionOutputDto {
    private Long id;
    private ReactionType type;
    private LocalDateTime createdAt;
    private UserSummaryDto user;
    private ReactionTargetType targetType;
    private Long targetId;
}

