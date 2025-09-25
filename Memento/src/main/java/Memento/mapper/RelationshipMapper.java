package Memento.mapper;

import Memento.dtos.OutputDto.RelationshipOutputDto;
import Memento.dtos.OutputDto.UserSummaryDto;
import Memento.dtos.InputDto.RelationshipUpdateDto;
import Memento.entities.Relationship;
import Memento.entities.User;

import java.util.Objects;

public class RelationshipMapper {

    public static void domainToDto(Relationship relationship, RelationshipOutputDto dto) {
        Objects.requireNonNull(relationship, "relationship must not be null");
        Objects.requireNonNull(dto, "dto must not be null");

        dto.setId(relationship.getId());
        dto.setType(relationship.getType());
        dto.setSince(relationship.getSince());

        // User A
        if (relationship.getUserA() != null && dto.getUserA() != null) {
            User userA = relationship.getUserA();
            UserSummaryDto userADto = dto.getUserA();
            userADto.setId(userA.getId());
            userADto.setName(userA.getName());
            userADto.setAvatarUrl(userA.getAvatarUrl());
        }

        // User B
        if (relationship.getUserB() != null && dto.getUserB() != null) {
            User userB = relationship.getUserB();
            UserSummaryDto userBDto = dto.getUserB();
            userBDto.setId(userB.getId());
            userBDto.setName(userB.getName());
            userBDto.setAvatarUrl(userB.getAvatarUrl());
        }
    }

    public static void dtoToDomain(RelationshipUpdateDto dto, Relationship relationship) {
        Objects.requireNonNull(dto, "dto must not be null");
        Objects.requireNonNull(relationship, "relationship must not be null");

        relationship.setType(dto.getType());

    }
}
