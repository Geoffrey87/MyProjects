package Memento.services;

import Memento.dtos.InputDto.CommentCreateDto;
import Memento.dtos.OutputDto.CommentOutputDto;
import Memento.entities.User;
import Memento.entities.enums.CommentTargetType;

import java.util.List;

public interface ICommentService {

    /**
     * Create a new comment.
     */
    CommentOutputDto create(CommentCreateDto dto, User author);

    /**
     * Get all comments for a specific target (post, media, story...).
     */
    List<CommentOutputDto> getByTarget(Long targetId, CommentTargetType targetType);

    /**
     * Delete a comment (only by author or admin).
     */
    void delete(Long commentId, User userEmail);
}

