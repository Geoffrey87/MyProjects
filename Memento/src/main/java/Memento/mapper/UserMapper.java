package Memento.mapper;

import Memento.dtos.OutputDto.UserMeDto;
import Memento.dtos.OutputDto.UserPublicDto;
import Memento.dtos.OutputDto.UserSummaryDto;
import Memento.entities.User;

import java.util.Objects;

public class UserMapper {

    public static void domainToSummary(User user, UserSummaryDto dto) {
        Objects.requireNonNull(user, "user must not be null");
        Objects.requireNonNull(dto, "dto must not be null");

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setAvatarUrl(user.getAvatarUrl());
    }

    public static void domainToPublic(User user, UserPublicDto dto) {
        Objects.requireNonNull(user, "user must not be null");
        Objects.requireNonNull(dto, "dto must not be null");

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setAvatarUrl(user.getAvatarUrl());
        dto.setLocation(user.getLocation());
    }

    public static void domainToMe(User user, UserMeDto dto) {
        Objects.requireNonNull(user, "user must not be null");
        Objects.requireNonNull(dto, "dto must not be null");

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setAvatarUrl(user.getAvatarUrl());
        dto.setLocation(user.getLocation());
        dto.setAge(user.getAge());
        dto.setCreatedAt(user.getCreatedAt());
    }
}
