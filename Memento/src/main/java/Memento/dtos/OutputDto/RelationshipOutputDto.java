package Memento.dtos.OutputDto;

import Memento.entities.enums.RelationshipType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RelationshipOutputDto {
    private Long id;
    private RelationshipType type;
    private LocalDate since;
    private UserSummaryDto userA;
    private UserSummaryDto userB;
}

