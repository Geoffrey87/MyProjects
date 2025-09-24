package Memento.repositories;

import Memento.entities.MemoryCapsule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemoryCapsuleRepository extends JpaRepository<MemoryCapsule, Long> {
    List<MemoryCapsule> findByPostId(Long postId);
    List<MemoryCapsule> findByMediaId(Long mediaId);
    List<MemoryCapsule> findByStoryId(Long storyId);
}

