package Memento.repositories;

import Memento.entities.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelationshipRepository extends JpaRepository<Relationship, Long> {
    List<Relationship> findByUserAIdOrUserBId(Long userAId, Long userBId);
}

