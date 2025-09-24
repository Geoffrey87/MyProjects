package Memento.repositories;

import Memento.entities.StoryReaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryReactionRepository extends JpaRepository<StoryReaction, Long> {
    List<StoryReaction> findByUserId(Long userId);
    List<StoryReaction> findByStoryId(Long storyId);
}

