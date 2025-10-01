package Memento.mapper;

import Memento.dtos.InputDto.PostCreateDto;
import Memento.dtos.OutputDto.MediaOutputDto;
import Memento.dtos.OutputDto.PostOutputDto;
import Memento.dtos.OutputDto.TagOutputDto;
import Memento.dtos.OutputDto.UserSummaryDto;
import Memento.entities.Media;
import Memento.entities.Post;
import Memento.entities.Tag;
import Memento.entities.User;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class PostMapper {

    public static void domainToDto(Post post, PostOutputDto dto) {
        Objects.requireNonNull(post, "post must not be null");
        Objects.requireNonNull(dto, "dto must not be null");

        dto.setId(post.getId());
        dto.setContent(post.getContent());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        dto.setVisibility(post.getVisibility());

        // Author
        if (post.getAuthor() != null) {
            User user = post.getAuthor();
            UserSummaryDto authorDto = new UserSummaryDto();
            authorDto.setId(user.getId());
            authorDto.setName(user.getName());
            authorDto.setAvatarUrl(user.getAvatarUrl());
            dto.setAuthor(authorDto);
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

        // Media
        if (post.getMedia() != null) {
            List<MediaOutputDto> mediaDtos = post.getMedia().stream()
                    .map(media -> {
                        MediaOutputDto m = new MediaOutputDto();
                        m.setId(media.getId());
                        m.setUrl(media.getUrl());
                        m.setType(media.getType());
                        return m;
                    })
                    .collect(Collectors.toList());
            dto.setMedia(mediaDtos);
        }
    }

    public static void dtoToDomain(PostCreateDto dto, Post post, User author, Set<Tag> tags, Set<Media> mediaList) {
        Objects.requireNonNull(dto, "dto must not be null");
        Objects.requireNonNull(post, "post must not be null");
        Objects.requireNonNull(author, "author must not be null");

        post.setContent(dto.getContent());
        post.setVisibility(dto.getVisibility());
        post.setAuthor(author);

        if (tags != null) {
            post.setTags(new HashSet<>(tags));
        }

        if (mediaList != null) {
            post.setMedia(new HashSet<>(mediaList));
        }
    }

    public static void dtoToDomain(PostCreateDto dto, Post post, User author, Set<Tag> tags) {
        dtoToDomain(dto, post, author, tags, null);
    }
}
