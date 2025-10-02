package Memento.mapper;

import Memento.dtos.InputDto.MediaCreateDto;
import Memento.dtos.OutputDto.MediaOutputDto;
import Memento.dtos.OutputDto.TagOutputDto;
import Memento.dtos.OutputDto.UserSummaryDto;
import Memento.entities.Album;
import Memento.entities.Media;
import Memento.entities.Tag;
import Memento.entities.User;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class MediaMapper {

    public static void domainToDto(Media media, MediaOutputDto dto, UserSummaryDto ownerDto, List<TagOutputDto> tagDtos) {
        Objects.requireNonNull(media, "media must not be null");
        Objects.requireNonNull(dto, "dto must not be null");

        dto.setId(media.getId());
        dto.setTitle(media.getTitle());
        dto.setType(media.getType());
        dto.setUrl(media.getUrl());
        dto.setSizeMB(media.getSizeMB());
        dto.setUploadedAt(media.getUploadedAt());
        dto.setVisibility(media.getVisibility());

        // Owner (only fills if provided)
        if (media.getOwner() != null && ownerDto != null) {
            ownerDto.setId(media.getOwner().getId());
            ownerDto.setName(media.getOwner().getName());
            ownerDto.setAvatarUrl(media.getOwner().getAvatarUrl());
            dto.setOwner(ownerDto);
        }

        // Album
        if (media.getAlbum() != null) {
            dto.setAlbumId(media.getAlbum().getId());
        }

        // Tags (pre-filled list passed in)
        if (media.getTags() != null && tagDtos != null) {
            dto.setTags(tagDtos);
        }
    }

    public static void dtoToDomain(MediaCreateDto dto, Media media, User owner, Album album, Set<Tag> tags) {
        Objects.requireNonNull(dto, "dto must not be null");
        Objects.requireNonNull(media, "media must not be null");
        Objects.requireNonNull(owner, "owner must not be null");

        media.setTitle(dto.getTitle());
        media.setType(dto.getType());
        media.setUrl(dto.getUrl());
        media.setVisibility(dto.getVisibility());
        media.setOwner(owner);

        if (album != null) {
            media.setAlbum(album);
        }
        if (tags != null && !tags.isEmpty()) {
            media.setTags(tags);
        }
    }
}

