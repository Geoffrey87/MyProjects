package Memento.mapper;

import Memento.dtos.InputDto.CommentCreateDto;
import Memento.dtos.OutputDto.CommentOutputDto;
import Memento.dtos.OutputDto.UserSummaryDto;
import Memento.entities.Comment;
import Memento.entities.User;

import java.util.Objects;

public class CommentMapper {

    public static CommentOutputDto domainToDto(Comment comment, CommentOutputDto dto) {
        Objects.requireNonNull(comment, "comment must not be null");
        Objects.requireNonNull(dto, "dto must not be null");

        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setTargetType(comment.getTargetType());
        dto.setTargetId(comment.getTargetId());

        // Author → UserSummaryDto
        if (comment.getAuthor() != null && dto.getAuthor() != null) {
            User author = comment.getAuthor();
            UserSummaryDto authorDto = dto.getAuthor();
            authorDto.setId(author.getId());
            authorDto.setName(author.getName());
            authorDto.setAvatarUrl(author.getAvatarUrl());
        }

        return dto;
    }

    public static Comment dtoToDomain(CommentCreateDto dto, Comment comment, User author) {
        Objects.requireNonNull(dto, "dto must not be null");
        Objects.requireNonNull(comment, "comment must not be null");
        Objects.requireNonNull(author, "author must not be null");

        comment.setContent(dto.getContent());
        comment.setTargetType(dto.getTargetType());
        comment.setTargetId(dto.getTargetId());
        comment.setAuthor(author);

        return comment;
    }
}


