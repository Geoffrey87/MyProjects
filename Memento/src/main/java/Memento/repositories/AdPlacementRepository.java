package Memento.repositories;

import Memento.entities.AdPlacement;
import Memento.entities.enums.AdStatus;
import Memento.entities.Category;
import Memento.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AdPlacementRepository extends JpaRepository<AdPlacement, Long> {

    // Find all active ads between start and end dates
    List<AdPlacement> findByStartAtBeforeAndEndAtAfter(LocalDateTime now1, LocalDateTime now2);

    // Find all active ads for a specific category
    List<AdPlacement> findByCategoryAndStartAtBeforeAndEndAtAfter(
            Category category,
            LocalDateTime now1,
            LocalDateTime now2
    );

    // Find all active ads for a specific tag
    List<AdPlacement> findByTagAndStartAtBeforeAndEndAtAfter(
            Tag tag,
            LocalDateTime now1,
            LocalDateTime now2
    );

    @Query("SELECT a FROM AdPlacement a " +
            "WHERE (a.category.id = :categoryId OR :categoryId IS NULL) " +
            "AND (a.tag.id = :tagId OR :tagId IS NULL) " +
            "AND a.startAt <= CURRENT_TIMESTAMP " +
            "AND a.endAt >= CURRENT_TIMESTAMP")
    List<AdPlacement> findActiveAds(Long categoryId, Long tagId);

    // All ads by status
    List<AdPlacement> findByStatus(AdStatus status);

    // Ads filtered by status + category
    List<AdPlacement> findByStatusAndCategoryId(AdStatus status, Long categoryId);

    // Ads filtered by status + tag
    List<AdPlacement> findByStatusAndTagId(AdStatus status, Long tagId);


}

