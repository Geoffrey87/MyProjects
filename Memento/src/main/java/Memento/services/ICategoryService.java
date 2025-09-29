package Memento.services;

import Memento.dtos.InputDto.CategoryCreateDto;
import Memento.dtos.OutputDto.CategoryOutputDto;

import java.util.List;

/**
 * Service interface for managing categories.
 */
public interface ICategoryService {

    /**
     * Create a new category.
     *
     * @param dto input DTO containing category details
     * @return created category as output DTO
     */
    CategoryOutputDto create(CategoryCreateDto dto);

    /**
     * Update an existing category.
     *
     * @param id  the category ID
     * @param dto input DTO containing updated category details
     * @return updated category as output DTO
     */
    CategoryOutputDto update(Long id, CategoryCreateDto dto);

    /**
     * Delete a category.
     *
     * @param id the category ID
     */
    void delete(Long id);

    /**
     * Retrieve category by its ID.
     *
     * @param id the category ID
     * @return category as output DTO
     */
    CategoryOutputDto getByIdDto(Long id);

    /**
     * Retrieve all categories.
     *
     * @return list of all categories
     */
    List<CategoryOutputDto> getAll();
}

