package Memento.mapper;

import Memento.dtos.InputDto.AlbumCreateDto;
import Memento.dtos.OutputDto.AlbumOutputDto;
import Memento.entities.Album;
import Memento.entities.User;

import java.util.Objects;

public class AlbumMapper {

    public static void domainToDto(Album album, AlbumOutputDto dto) {
        dto.setId(album.getId());
        dto.setTitle(album.getTitle());
        dto.setDescription(album.getDescription());
        dto.setCreatedAt(album.getCreatedAt());
        dto.setVisibility(album.getVisibility());
        if (album.getOwner() != null && dto.getOwner() != null) {
            UserMapper.domainToSummary(album.getOwner(), dto.getOwner());
        }
        dto.setMediaCount(album.getMedia() != null ? album.getMedia().size() : 0);
    }

    public static void dtoToDomain(AlbumCreateDto dto, Album album, User owner) {

        album.setTitle(dto.getTitle());
        album.setDescription(dto.getDescription());
        album.setVisibility(dto.getVisibility());
        album.setOwner(owner);
    }
}
