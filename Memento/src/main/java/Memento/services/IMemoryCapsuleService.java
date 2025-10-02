package Memento.services;

import Memento.dtos.InputDto.MemoryCapsuleCreateDto;
import Memento.dtos.OutputDto.MemoryCapsuleOutputDto;

import java.util.List;

public interface IMemoryCapsuleService {

    /**
     * Create a new memory capsule (linked to a post, media, or story).
     */
    MemoryCapsuleOutputDto create(MemoryCapsuleCreateDto dto, String userEmail);

    /**
     * Retrieve a capsule by its ID.
     */
    MemoryCapsuleOutputDto getById(Long id);

    /**
     * Retrieve all capsules belonging to a specific user.
     */
    List<MemoryCapsuleOutputDto> getByUser(String userEmail);

    /**
     * Publish (make visible) a capsule when its unlock time arrives.
     */
    MemoryCapsuleOutputDto publish(Long id, String userEmail);

    /**
     * Delete a capsule (only owner can delete).
     */
    void delete(Long id, String userEmail);
}
