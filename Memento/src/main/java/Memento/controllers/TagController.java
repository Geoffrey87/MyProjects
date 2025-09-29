package Memento.controllers;

import Memento.dtos.InputDto.TagCreateDto;
import Memento.dtos.OutputDto.TagOutputDto;
import Memento.services.ITagService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final ITagService tagService;

    public TagController(ITagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public ResponseEntity<TagOutputDto> create(@Valid @RequestBody TagCreateDto dto) {
        return ResponseEntity.ok(tagService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagOutputDto> update(@PathVariable Long id,
                                               @Valid @RequestBody TagCreateDto dto) {
        return ResponseEntity.ok(tagService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagOutputDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.getByIdDto(id));
    }

    @GetMapping
    public ResponseEntity<List<TagOutputDto>> getAll() {
        return ResponseEntity.ok(tagService.getAll());
    }
}

