package Memento.repositories;

import Memento.entities.ProfileMusic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileMusicRepository extends JpaRepository<ProfileMusic, Long> {
    List<ProfileMusic> findByUserIdOrderByAddedAtDesc(Long userId);
    Optional<ProfileMusic> findFirstByUserIdAndIsActiveTrueOrderByAddedAtDesc(Long userId);
}

