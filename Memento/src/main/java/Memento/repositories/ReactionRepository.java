package Memento.repositories;

import Memento.entities.Reaction;
import Memento.entities.ReactionTargetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    List<Reaction> findByUserId(Long userId);
    List<Reaction> findByTargetIdAndTargetType(Long targetId, ReactionTargetType targetType);
}
