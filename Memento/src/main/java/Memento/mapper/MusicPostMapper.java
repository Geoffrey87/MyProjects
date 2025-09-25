package Memento.mapper;

import Memento.dtos.InputDto.MusicPostCreateDto;
import Memento.dtos.OutputDto.MusicPostOutputDto;
import Memento.dtos.OutputDto.UserSummaryDto;
import Memento.entities.MusicPost;
import Memento.entities.User;

import java.util.Objects;

public class MusicPostMapper {

    public static void domainToDto(MusicPost musicPost, MusicPostOutputDto dto) {
        Objects.requireNonNull(musicPost, "musicPost must not be null");
        Objects.requireNonNull(dto, "dto must not be null");

        dto.setId(musicPost.getId());
        dto.setPlatform(musicPost.getPlatform());
        dto.setTrackUrl(musicPost.getTrackUrl());
        dto.setTitle(musicPost.getTitle());
        dto.setArtist(musicPost.getArtist());
        dto.setCoverUrl(musicPost.getCoverUrl());
        dto.setSharedAt(musicPost.getSharedAt());
        dto.setVisibility(musicPost.getVisibility());

        if (musicPost.getAuthor() != null && dto.getAuthor() != null) {
            User user = musicPost.getAuthor();
            UserSummaryDto authorDto = dto.getAuthor();
            authorDto.setId(user.getId());
            authorDto.setName(user.getName());
            authorDto.setAvatarUrl(user.getAvatarUrl());
        }
    }

    public static void dtoToDomain(MusicPostCreateDto dto, MusicPost musicPost, User author) {
        Objects.requireNonNull(dto, "dto must not be null");
        Objects.requireNonNull(musicPost, "musicPost must not be null");
        Objects.requireNonNull(author, "author must not be null");

        musicPost.setPlatform(dto.getPlatform());
        musicPost.setTrackUrl(dto.getTrackUrl());
        musicPost.setTitle(dto.getTitle());
        musicPost.setArtist(dto.getArtist());
        musicPost.setCoverUrl(dto.getCoverUrl());
        musicPost.setVisibility(dto.getVisibility());
        musicPost.setAuthor(author);
    }
}
