package Memento.repositories;

import Memento.entities.Comment;
import Memento.entities.enums.CommentTargetType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByAuthorId(Long authorId);

    @EntityGraph(value = "Comment.withAuthor", type = org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.LOAD)
    List<Comment> findByTargetIdAndTargetType(Long targetId, CommentTargetType targetType);
}
