package Memento.services;

import Memento.dtos.InputDto.MediaCreateDto;
import Memento.dtos.OutputDto.MediaOutputDto;

import java.util.List;

public interface IMediaService {

    /**
     * Upload a new media item (photo/video).
     *
     * @param dto   input data for the media
     * @param email authenticated user email (owner)
     * @return created media as DTO
     */
    MediaOutputDto upload(MediaCreateDto dto, String email);

    /**
     * Get media details by ID.
     *
     * @param id media ID
     * @return media DTO
     */
    MediaOutputDto getById(Long id);

    /**
     * Get all media uploaded by a specific user.
     *
     * @param userId ID of the user
     * @return list of media
     */
    List<MediaOutputDto> getByUser(Long userId);

    /**
     * Delete a media item if it belongs to the authenticated user.
     *
     * @param id    media ID
     * @param email authenticated user email
     */
    void delete(Long id, String email);
}

