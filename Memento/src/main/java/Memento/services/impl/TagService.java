package Memento.services.impl;

import Memento.dtos.InputDto.TagCreateDto;
import Memento.dtos.OutputDto.TagOutputDto;
import Memento.entities.Category;
import Memento.entities.Tag;
import Memento.exception.ResourceNotFoundException;
import Memento.mapper.TagMapper;
import Memento.repositories.CategoryRepository;
import Memento.repositories.TagRepository;
import Memento.services.ITagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TagService implements ITagService {

    private final TagRepository tagRepository;
    private final CategoryRepository categoryRepository;

    public TagService(TagRepository tagRepository, CategoryRepository categoryRepository) {
        this.tagRepository = tagRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public TagOutputDto create(TagCreateDto dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + dto.getCategoryId()));

        Tag tag = new Tag();
        TagMapper.dtoToDomain(dto, tag, category);

        Tag saved = tagRepository.save(tag);
        TagOutputDto output = new TagOutputDto();
        TagMapper.domainToDto(saved, output);
        return output;
    }

    @Override
    public TagOutputDto update(Long id, TagCreateDto dto) {
        Tag tag = getById(id);

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + dto.getCategoryId()));

        TagMapper.dtoToDomain(dto, tag, category);

        Tag updated = tagRepository.save(tag);
        TagOutputDto output = new TagOutputDto();
        TagMapper.domainToDto(updated, output);
        return output;
    }

    @Override
    public void delete(Long id) {
        Tag tag = getById(id);
        tagRepository.delete(tag);
    }

    @Override
    public TagOutputDto getByIdDto(Long id) {
        Tag tag = getById(id);
        TagOutputDto output = new TagOutputDto();
        TagMapper.domainToDto(tag, output);
        return output;
    }

    @Override
    public List<TagOutputDto> getAll() {
        return tagRepository.findAll().stream()
                .map(tag -> {
                    TagOutputDto dto = new TagOutputDto();
                    TagMapper.domainToDto(tag, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // -----------------------------
    // Helpers
    // -----------------------------
    private Tag getById(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found with id " + id));
    }
}
