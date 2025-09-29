package Memento.services.impl;

import Memento.dtos.InputDto.CategoryCreateDto;
import Memento.dtos.OutputDto.CategoryOutputDto;
import Memento.entities.Category;
import Memento.exception.ResourceNotFoundException;
import Memento.mapper.CategoryMapper;
import Memento.repositories.CategoryRepository;
import Memento.services.ICategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryOutputDto create(CategoryCreateDto dto) {
        Category category = new Category();
        CategoryMapper.dtoToDomain(dto, category);

        Category saved = categoryRepository.save(category);
        CategoryOutputDto output = new CategoryOutputDto();
        CategoryMapper.domainToDto(saved, output);
        return output;
    }

    @Override
    public CategoryOutputDto update(Long id, CategoryCreateDto dto) {
        Category category = getById(id);

        CategoryMapper.dtoToDomain(dto, category);
        Category updated = categoryRepository.save(category);

        CategoryOutputDto output = new CategoryOutputDto();
        CategoryMapper.domainToDto(updated, output);
        return output;
    }

    @Override
    public void delete(Long id) {
        Category category = getById(id);
        categoryRepository.delete(category);
    }

    @Override
    public CategoryOutputDto getByIdDto(Long id) {
        Category category = getById(id);
        CategoryOutputDto output = new CategoryOutputDto();
        CategoryMapper.domainToDto(category, output);
        return output;
    }

    @Override
    public List<CategoryOutputDto> getAll() {
        return categoryRepository.findAll().stream()
                .map(category -> {
                    CategoryOutputDto dto = new CategoryOutputDto();
                    CategoryMapper.domainToDto(category, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // -----------------------------
    // Helpers
    // -----------------------------
    private Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
    }
}

