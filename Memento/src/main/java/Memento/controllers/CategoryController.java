package Memento.controllers;

import Memento.dtos.InputDto.CategoryCreateDto;
import Memento.dtos.OutputDto.CategoryOutputDto;
import Memento.services.ICategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryOutputDto> create(@Valid @RequestBody CategoryCreateDto dto) {
        return ResponseEntity.ok(categoryService.create(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryOutputDto> update(@PathVariable Long id,
                                                    @Valid @RequestBody CategoryCreateDto dto) {
        return ResponseEntity.ok(categoryService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryOutputDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getByIdDto(id));
    }

    @GetMapping
    public ResponseEntity<List<CategoryOutputDto>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }
}
