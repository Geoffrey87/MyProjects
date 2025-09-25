package Memento.mapper;

import Memento.dtos.InputDto.MemoryCapsuleCreateDto;
import Memento.dtos.OutputDto.MemoryCapsuleOutputDto;
import Memento.entities.MemoryCapsule;
import Memento.entities.Media;
import Memento.entities.Post;
import Memento.entities.Story;

import java.util.Objects;

public class MemoryCapsuleMapper {

    public static void domainToDto(MemoryCapsule capsule, MemoryCapsuleOutputDto dto) {
        Objects.requireNonNull(capsule, "capsule must not be null");
        Objects.requireNonNull(dto, "dto must not be null");

        dto.setId(capsule.getId());
        dto.setUnlockAt(capsule.getUnlockAt());
        dto.setIsPublished(capsule.getIsPublished());

        if (capsule.getPost() != null) {
            dto.setPostId(capsule.getPost().getId());
        }
        if (capsule.getMedia() != null) {
            dto.setMediaId(capsule.getMedia().getId());
        }
        if (capsule.getStory() != null) {
            dto.setStoryId(capsule.getStory().getId());
        }
    }

    public static void dtoToDomain(MemoryCapsuleCreateDto dto, MemoryCapsule capsule,
                                   Post post, Media media, Story story) {
        Objects.requireNonNull(dto, "dto must not be null");
        Objects.requireNonNull(capsule, "capsule must not be null");

        capsule.setUnlockAt(dto.getUnlockAt());
        capsule.setIsPublished(dto.getIsPublished() != null ? dto.getIsPublished() : false);

        capsule.setPost(post);     // pode ser null
        capsule.setMedia(media);   // pode ser null
        capsule.setStory(story);   // pode ser null
    }
}
