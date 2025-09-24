package Memento.repositories;

import Memento.entities.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @EntityGraph(value = "Post.withAuthor", type = org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.LOAD)
    List<Post> findByAuthorIdOrderByCreatedAtDesc(Long authorId);
}
