package Memento.repositories;

import Memento.entities.Media;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    List<Media> findByOwnerId(Long ownerId);

    @EntityGraph(value = "Media.withOwnerAndAlbum", type = org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.LOAD)
    List<Media> findByAlbumId(Long albumId);
}
