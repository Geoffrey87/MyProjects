package Memento.repositories;

import Memento.entities.ProfileMusic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfileMusicRepository extends JpaRepository<ProfileMusic, Long> {

    List<ProfileMusic> findByUserId(Long userId);

    Optional<ProfileMusic> findFirstByUserIdAndIsActiveTrue(Long userId);
}


