package Memento.mapper;

import Memento.dtos.InputDto.CategoryCreateDto;
import Memento.dtos.OutputDto.CategoryOutputDto;
import Memento.entities.Category;

import java.util.Objects;

public class CategoryMapper {

    public static void domainToDto(Category category, CategoryOutputDto dto) {
        Objects.requireNonNull(category, "category must not be null");
        Objects.requireNonNull(dto, "dto must not be null");

        dto.setId(category.getId());
        dto.setName(category.getName());
    }

    public static void dtoToDomain(CategoryCreateDto dto, Category category) {
        Objects.requireNonNull(dto, "dto must not be null");
        Objects.requireNonNull(category, "category must not be null");

        category.setName(dto.getName());
    }
}
