package Memento.repositories;

import Memento.entities.Album;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findByOwnerId(Long ownerId);

    @Query("select a from Album a where a.id = :id")
    @EntityGraph(value = "Album.withMedia", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Album> findWithMediaById(Long id);
}
