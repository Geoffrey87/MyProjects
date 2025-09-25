package Memento.mapper;

import Memento.dtos.InputDto.StoryCreateDto;
import Memento.dtos.OutputDto.StoryOutputDto;
import Memento.dtos.OutputDto.TagOutputDto;
import Memento.dtos.OutputDto.UserSummaryDto;
import Memento.entities.Story;
import Memento.entities.Tag;
import Memento.entities.User;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class StoryMapper {

    public static void domainToDto(Story story, StoryOutputDto dto) {
        Objects.requireNonNull(story, "story must not be null");
        Objects.requireNonNull(dto, "dto must not be null");

        dto.setId(story.getId());
        dto.setType(story.getType());
        dto.setUrl(story.getUrl());
        dto.setSizeMB(story.getSizeMB());
        dto.setCreatedAt(story.getCreatedAt());
        dto.setExpiresAt(story.getExpiresAt());
        dto.setVisibility(story.getVisibility());

        // Author → UserSummaryDto
        if (story.getAuthor() != null && dto.getAuthor() != null) {
            UserSummaryDto authorDto = dto.getAuthor();
            authorDto.setId(story.getAuthor().getId());
            authorDto.setName(story.getAuthor().getName());
            authorDto.setAvatarUrl(story.getAuthor().getAvatarUrl());
        }

        // Tags → List<TagOutputDto>
        if (story.getTags() != null) {
            List<TagOutputDto> tagDtos = story.getTags().stream()
                    .map(tag -> {
                        TagOutputDto t = new TagOutputDto();
                        t.setId(tag.getId());
                        t.setName(tag.getName());
                        if (tag.getCategory() != null) {
                            t.setCategoryId(tag.getCategory().getId());
                        }
                        return t;
                    })
                    .collect(Collectors.toList());
            dto.setTags(tagDtos);
        }
    }

    public static void dtoToDomain(StoryCreateDto dto, Story story, User author, List<Tag> tags) {
        Objects.requireNonNull(dto, "dto must not be null");
        Objects.requireNonNull(story, "story must not be null");
        Objects.requireNonNull(author, "author must not be null");

        story.setType(dto.getType());
        story.setUrl(dto.getUrl());
        story.setSizeMB(dto.getSizeMB());
        story.setExpiresAt(dto.getExpiresAt());
        story.setVisibility(dto.getVisibility());
        story.setAuthor(author);

        if (tags != null) {
            story.setTags(tags.stream().collect(Collectors.toSet()));
        }
    }
}
