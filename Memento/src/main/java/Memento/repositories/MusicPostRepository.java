package Memento.repositories;

import Memento.entities.MusicPost;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicPostRepository extends JpaRepository<MusicPost, Long> {
    @EntityGraph(value = "MusicPost.withAuthor", type = org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.LOAD)
    List<MusicPost> findByAuthorIdOrderBySharedAtDesc(Long authorId);
}
