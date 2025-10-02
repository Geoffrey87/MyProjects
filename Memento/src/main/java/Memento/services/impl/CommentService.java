package Memento.services.impl;

import Memento.dtos.InputDto.CommentCreateDto;
import Memento.dtos.OutputDto.CommentOutputDto;
import Memento.entities.Comment;
import Memento.entities.User;
import Memento.entities.enums.CommentTargetType;
import Memento.exception.ResourceNotFoundException;
import Memento.mapper.CommentMapper;
import Memento.repositories.CommentRepository;
import Memento.repositories.UserRepository;
import Memento.services.ICommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentService implements ICommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository,
                          UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CommentOutputDto create(CommentCreateDto dto, User author) {
        Comment comment = new Comment();
        CommentMapper.dtoToDomain(dto, comment, author);

        Comment saved = commentRepository.save(comment);

        CommentOutputDto output = new CommentOutputDto();
        CommentMapper.domainToDto(saved, output);
        return output;
    }

    @Override
    public List<CommentOutputDto> getByTarget(Long targetId, CommentTargetType targetType) {
        return commentRepository.findByTargetIdAndTargetType(targetId, targetType).stream()
                .map(comment -> {
                    CommentOutputDto dto = new CommentOutputDto();
                    CommentMapper.domainToDto(comment, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long commentId, User author) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + commentId));

        // Only allow author OR admin to delete
        boolean isAuthor = comment.getAuthor().getId().equals(author.getId());
        boolean isAdmin = author.getRoles().stream()
                .anyMatch(role -> role.getName().name().equals("ADMIN"));

        if (!isAuthor && !isAdmin) {
            throw new SecurityException("You can only delete your own comments or be an ADMIN");
        }

        commentRepository.delete(comment);
    }

}

