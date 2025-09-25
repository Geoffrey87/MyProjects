package Memento.mapper;

import Memento.dtos.InputDto.PostCreateDto;
import Memento.dtos.OutputDto.PostOutputDto;
import Memento.dtos.OutputDto.TagOutputDto;
import Memento.dtos.OutputDto.UserSummaryDto;
import Memento.entities.Post;
import Memento.entities.Tag;
import Memento.entities.User;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PostMapper {

    public static void domainToDto(Post post, PostOutputDto dto) {
        Objects.requireNonNull(post, "post must not be null");
        Objects.requireNonNull(dto, "dto must not be null");

        dto.setId(post.getId());
        dto.setContent(post.getContent());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setVisibility(post.getVisibility());

        // Author
        if (post.getAuthor() != null && dto.getAuthor() != null) {
            User user = post.getAuthor();
            UserSummaryDto authorDto = dto.getAuthor();
            authorDto.setId(user.getId());
            authorDto.setName(user.getName());
            authorDto.setAvatarUrl(user.getAvatarUrl());
        }

        // Tags
        if (post.getTags() != null) {
            List<TagOutputDto> tagDtos = post.getTags().stream()
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

    public static void dtoToDomain(PostCreateDto dto, Post post, User author, List<Tag> tags) {
        Objects.requireNonNull(dto, "dto must not be null");
        Objects.requireNonNull(post, "post must not be null");
        Objects.requireNonNull(author, "author must not be null");

        post.setContent(dto.getContent());
        post.setVisibility(dto.getVisibility());
        post.setAuthor(author);

        if (tags != null) {
            post.setTags(tags.stream().collect(Collectors.toSet()));
        }

    }
}
