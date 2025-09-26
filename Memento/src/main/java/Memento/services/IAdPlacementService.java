package Memento.services;

import Memento.dtos.InputDto.AdPlacementCreateDto;
import Memento.dtos.OutputDto.AdPlacementOutputDto;

import java.util.List;

/**
 * Service interface for managing advertisement placements.
 * Provides operations for creating, moderating, and retrieving ads.
 */
public interface IAdPlacementService {

    // -----------------------------
    // Creation
    // -----------------------------

    /**
     * Create a new ad placement request (initially pending approval).
     *
     * @param dto input DTO containing ad placement details
     * @return created ad placement as output DTO
     */
    AdPlacementOutputDto create(AdPlacementCreateDto dto);

    // -----------------------------
    // Moderation (admin only)
    // -----------------------------

    /**
     * Approve an ad placement (moves from PENDING → APPROVED).
     *
     * @param id the ad ID
     * @return updated ad placement
     */
    AdPlacementOutputDto approve(Long id);

    /**
     * Activate an ad placement (makes it visible to users,
     * if within valid start/end period).
     *
     * @param id the ad ID
     * @return updated ad placement
     */
    AdPlacementOutputDto activate(Long id);

    /**
     * Expire an ad placement (mark as EXPIRED, no longer shown).
     *
     * @param id the ad ID
     * @return updated ad placement
     */
    AdPlacementOutputDto expire(Long id);

    // -----------------------------
    // Retrieval (public endpoints)
    // -----------------------------

    /**
     * Retrieve all currently active ad placements.
     *
     * @return list of active ads
     */
    List<AdPlacementOutputDto> getActiveAds();

    /**
     * Retrieve all active ads for a given category.
     *
     * @param categoryId category ID
     * @return list of active ads filtered by category
     */
    List<AdPlacementOutputDto> getActiveAdsByCategory(Long categoryId);

    /**
     * Retrieve all active ads for a given tag.
     *
     * @param tagId tag ID
     * @return list of active ads filtered by tag
     */
    List<AdPlacementOutputDto> getActiveAdsByTag(Long tagId);
}
