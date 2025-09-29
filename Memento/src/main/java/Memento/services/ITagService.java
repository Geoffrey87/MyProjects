package Memento.services;

import Memento.dtos.InputDto.TagCreateDto;
import Memento.dtos.OutputDto.TagOutputDto;

import java.util.List;

/**
 * Service interface for managing tags.
 */
public interface ITagService {

    /**
     * Create a new tag.
     *
     * @param dto input DTO containing tag details
     * @return created tag as output DTO
     */
    TagOutputDto create(TagCreateDto dto);

    /**
     * Update an existing tag.
     *
     * @param id  the tag ID
     * @param dto input DTO containing updated tag details
     * @return updated tag as output DTO
     */
    TagOutputDto update(Long id, TagCreateDto dto);

    /**
     * Delete a tag.
     *
     * @param id the tag ID
     */
    void delete(Long id);

    /**
     * Retrieve tag by its ID.
     *
     * @param id the tag ID
     * @return tag as output DTO
     */
    TagOutputDto getByIdDto(Long id);

    /**
     * Retrieve all tags.
     *
     * @return list of all tags
     */
    List<TagOutputDto> getAll();
}

