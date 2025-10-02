package Memento.services;

import Memento.dtos.InputDto.AlbumCreateDto;
import Memento.dtos.OutputDto.AlbumOutputDto;

import java.util.List;

public interface IAlbumService {

    /**
     * Create a new album for the authenticated user.
     *
     * @param dto   album details
     * @param email authenticated user email
     * @return created album DTO
     */
    AlbumOutputDto create(AlbumCreateDto dto, String email);

    /**
     * Update an album (only if owner).
     *
     * @param id    album id
     * @param dto   new details
     * @param email authenticated user email
     * @return updated album DTO
     */
    AlbumOutputDto update(Long id, AlbumCreateDto dto, String email);

    /**
     * Delete an album (only if owner).
     *
     * @param id    album id
     * @param email authenticated user email
     */
    void delete(Long id, String email);

    /**
     * Get album details by id.
     *
     * @param id album id
     * @return album DTO
     */
    AlbumOutputDto getById(Long id);

    /**
     * Get all albums of a user.
     *
     * @param userId user id
     * @return list of albums
     */
    List<AlbumOutputDto> getByUser(Long userId);
}

