package Memento.repositories;

import Memento.entities.Story;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {
    @EntityGraph(value = "Story.withAuthor", type = org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.LOAD)
    List<Story> findByAuthorId(Long authorId);
}
