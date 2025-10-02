package Memento.mapper;

import Memento.dtos.InputDto.AlbumCreateDto;
import Memento.dtos.OutputDto.AlbumOutputDto;
import Memento.dtos.OutputDto.UserSummaryDto;
import Memento.entities.Album;
import Memento.entities.User;

import java.util.Objects;


public class AlbumMapper {

    public static void domainToDto(Album album, AlbumOutputDto dto, UserSummaryDto ownerDto) {
        Objects.requireNonNull(album, "album must not be null");
        Objects.requireNonNull(dto, "dto must not be null");

        dto.setId(album.getId());
        dto.setTitle(album.getTitle());
        dto.setDescription(album.getDescription());
        dto.setCreatedAt(album.getCreatedAt());
        dto.setVisibility(album.getVisibility());

        // Owner (only fill if provided)
        if (album.getOwner() != null && ownerDto != null) {
            UserMapper.domainToSummary(album.getOwner(), ownerDto);
            dto.setOwner(ownerDto);
        }

        // Media count
        dto.setMediaCount(album.getMedia() != null ? album.getMedia().size() : 0);
    }

    public static void dtoToDomain(AlbumCreateDto dto, Album album, User owner) {
        Objects.requireNonNull(dto, "dto must not be null");
        Objects.requireNonNull(album, "album must not be null");
        Objects.requireNonNull(owner, "owner must not be null");

        album.setTitle(dto.getTitle());
        album.setDescription(dto.getDescription());
        album.setVisibility(dto.getVisibility());
        album.setOwner(owner);
    }
}
