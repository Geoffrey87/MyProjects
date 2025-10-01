package Memento.services;

import Memento.dtos.InputDto.PostCreateDto;
import Memento.dtos.OutputDto.PostOutputDto;

import java.util.List;

public interface IPostService {

    /**
     * Create a new post for the authenticated user.
     */
    PostOutputDto create(PostCreateDto dto, String authorEmail);

    /**
     * Update an existing post (only author can update).
     */
    PostOutputDto update(Long id, PostCreateDto dto, String authorEmail);

    /**
     * Delete a post (only author can delete).
     */
    void delete(Long id, String authorEmail);

    /**
     * Get a post by ID.
     */
    PostOutputDto getById(Long id);

    /**
     * Get all posts visible to the current user (timeline).
     */
    List<PostOutputDto> getTimeline(String viewerEmail);

    /**
     * Get all posts from a specific user (public profile).
     */
    List<PostOutputDto> getByUser(Long userId, String viewerEmail);
}

