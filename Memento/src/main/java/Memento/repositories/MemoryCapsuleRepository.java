package Memento.repositories;

import Memento.entities.MemoryCapsule;
import Memento.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemoryCapsuleRepository extends JpaRepository<MemoryCapsule, Long> {

    /**
     * Get all capsules linked to posts, media or stories of a specific user.
     *
     * Note: this assumes relationships are mapped with Post.author, Media.owner, Story.author.
     */
    List<MemoryCapsule> findByPostAuthorOrMediaOwnerOrStoryAuthor(User postAuthor, User mediaOwner, User storyAuthor);
}


