package Memento.mapper;

import Memento.dtos.InputDto.CommentCreateDto;
import Memento.dtos.InputDto.MemoryCapsuleCreateDto;
import Memento.dtos.OutputDto.CommentOutputDto;
import Memento.dtos.OutputDto.MemoryCapsuleOutputDto;
import Memento.dtos.OutputDto.UserSummaryDto;
import Memento.entities.*;

import java.util.Objects;

public class MemoryCapsuleMapper {

    public static MemoryCapsuleOutputDto domainToDto(
            MemoryCapsule capsule,
            MemoryCapsuleOutputDto dto
    ) {
        Objects.requireNonNull(capsule, "capsule must not be null");
        Objects.requireNonNull(dto, "dto must not be null");

        dto.setId(capsule.getId());
        dto.setUnlockAt(capsule.getUnlockAt());
        dto.setIsPublished(capsule.getIsPublished());
        dto.setPostId(capsule.getPost() != null ? capsule.getPost().getId() : null);
        dto.setMediaId(capsule.getMedia() != null ? capsule.getMedia().getId() : null);
        dto.setStoryId(capsule.getStory() != null ? capsule.getStory().getId() : null);

        return dto;
    }


    public static MemoryCapsule dtoToDomain(
            MemoryCapsuleCreateDto dto,
            MemoryCapsule capsule,
            Post post,
            Media media,
            Story story
    ) {
        Objects.requireNonNull(dto, "dto must not be null");
        Objects.requireNonNull(capsule, "capsule must not be null");

        capsule.setUnlockAt(dto.getUnlockAt());
        capsule.setIsPublished(dto.getIsPublished() != null ? dto.getIsPublished() : false);

        capsule.setPost(post);
        capsule.setMedia(media);
        capsule.setStory(story);

        return capsule;
    }
}


