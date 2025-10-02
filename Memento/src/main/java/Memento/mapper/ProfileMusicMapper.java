package Memento.mapper;

import Memento.dtos.InputDto.ProfileMusicCreateDto;
import Memento.dtos.OutputDto.ProfileMusicOutputDto;
import Memento.entities.ProfileMusic;
import Memento.entities.User;

import java.util.Objects;

public class ProfileMusicMapper {

    public static ProfileMusicOutputDto domainToDto(ProfileMusic profileMusic, ProfileMusicOutputDto dto) {
        Objects.requireNonNull(profileMusic, "profileMusic must not be null");
        Objects.requireNonNull(dto, "dto must not be null");

        dto.setId(profileMusic.getId());
        dto.setPlatform(profileMusic.getPlatform());
        dto.setTrackUrl(profileMusic.getTrackUrl());
        dto.setTitle(profileMusic.getTitle());
        dto.setArtist(profileMusic.getArtist());
        dto.setAddedAt(profileMusic.getAddedAt());
        dto.setIsActive(profileMusic.getIsActive());

        return dto;
    }

    public static ProfileMusic dtoToDomain(ProfileMusicCreateDto dto, ProfileMusic profileMusic, User user) {
        Objects.requireNonNull(dto, "dto must not be null");
        Objects.requireNonNull(profileMusic, "profileMusic must not be null");
        Objects.requireNonNull(user, "user must not be null");

        profileMusic.setPlatform(dto.getPlatform());
        profileMusic.setTrackUrl(dto.getTrackUrl());
        profileMusic.setTitle(dto.getTitle());
        profileMusic.setArtist(dto.getArtist());
        profileMusic.setIsActive(dto.getIsActive());
        profileMusic.setUser(user);

        return profileMusic;
    }
}

