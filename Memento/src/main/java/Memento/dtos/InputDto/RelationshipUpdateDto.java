package Memento.dtos.InputDto;

import Memento.entities.RelationshipType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RelationshipUpdateDto {

    @NotNull(message = "Relationship id is required")
    @Positive(message = "Relationship id must be positive")
    private Long relationshipId;

    @NotNull(message = "Type is required")
    private RelationshipType type;
}

