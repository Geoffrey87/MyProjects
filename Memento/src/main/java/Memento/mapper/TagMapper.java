package Memento.mapper;

import Memento.dtos.InputDto.TagCreateDto;
import Memento.dtos.OutputDto.TagOutputDto;
import Memento.entities.Category;
import Memento.entities.Tag;

import java.util.Objects;

public class TagMapper {

    public static void domainToDto(Tag tag, TagOutputDto dto) {
        Objects.requireNonNull(tag, "tag must not be null");
        Objects.requireNonNull(dto, "dto must not be null");

        dto.setId(tag.getId());
        dto.setName(tag.getName());
        if (tag.getCategory() != null) {
            dto.setCategoryId(tag.getCategory().getId());
        }
    }

    public static void dtoToDomain(TagCreateDto dto, Tag tag, Category category) {
        Objects.requireNonNull(dto, "dto must not be null");
        Objects.requireNonNull(tag, "tag must not be null");
        Objects.requireNonNull(category, "category must not be null");

        tag.setName(dto.getName());
        tag.setCategory(category);
    }
}
