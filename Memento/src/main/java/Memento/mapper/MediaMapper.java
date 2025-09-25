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
import java.util.stream.Collectors;

public class MediaMapper {

    public static void domainToDto(Media media, MediaOutputDto dto) {
        Objects.requireNonNull(media, "media must not be null");
        Objects.requireNonNull(dto, "dto must not be null");

        dto.setId(media.getId());
        dto.setTitle(media.getTitle());
        dto.setType(media.getType());
        dto.setUrl(media.getUrl());
        dto.setSizeMB(media.getSizeMB());
        dto.setUploadedAt(media.getUploadedAt());
        dto.setVisibility(media.getVisibility());

        // Owner
        if (media.getOwner() != null && dto.getOwner() != null) {
            User user = media.getOwner();
            UserSummaryDto ownerDto = dto.getOwner();
            ownerDto.setId(user.getId());
            ownerDto.setName(user.getName());
            ownerDto.setAvatarUrl(user.getAvatarUrl());
        }

        // Album
        if (media.getAlbum() != null) {
            dto.setAlbumId(media.getAlbum().getId());
        }

        // Tags
        if (media.getTags() != null) {
            List<TagOutputDto> tagDtos = media.getTags().stream()
                    .map(tag -> {
                        TagOutputDto t = new TagOutputDto();
                        t.setId(tag.getId());
                        t.setName(tag.getName());
                        if (tag.getCategory() != null) {
                            t.setCategoryId(tag.getCategory().getId());
                        }
                        return t;
                    })
                    .collect(Collectors.toList());
            dto.setTags(tagDtos);
        }
    }

    public static void dtoToDomain(MediaCreateDto dto, Media media, User owner, Album album, List<Tag> tags) {
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
        if (tags != null) {
            media.setTags(tags.stream().collect(Collectors.toSet()));
        }
    }
}
