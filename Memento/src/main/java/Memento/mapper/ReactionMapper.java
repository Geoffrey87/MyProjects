package Memento.mapper;

import Memento.dtos.InputDto.ReactionCreateDto;
import Memento.dtos.OutputDto.ReactionOutputDto;
import Memento.dtos.OutputDto.UserSummaryDto;
import Memento.entities.Reaction;
import Memento.entities.User;

import java.util.Objects;

public class ReactionMapper {

    public static void domainToDto(Reaction reaction, ReactionOutputDto dto) {
        Objects.requireNonNull(reaction, "reaction must not be null");
        Objects.requireNonNull(dto, "dto must not be null");

        dto.setId(reaction.getId());
        dto.setType(reaction.getType());
        dto.setCreatedAt(reaction.getCreatedAt());
        dto.setTargetType(reaction.getTargetType());
        dto.setTargetId(reaction.getTargetId());

        if (reaction.getUser() != null && dto.getUser() != null) {
            User user = reaction.getUser();
            UserSummaryDto userDto = dto.getUser();
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setAvatarUrl(user.getAvatarUrl());
        }
    }

    public static void dtoToDomain(ReactionCreateDto dto, Reaction reaction, User user) {
        Objects.requireNonNull(dto, "dto must not be null");
        Objects.requireNonNull(reaction, "reaction must not be null");
        Objects.requireNonNull(user, "user must not be null");

        reaction.setType(dto.getType());
        reaction.setTargetType(dto.getTargetType());
        reaction.setTargetId(dto.getTargetId());
        reaction.setUser(user);
    }
}
