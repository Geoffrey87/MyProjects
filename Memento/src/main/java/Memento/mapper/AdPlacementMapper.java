package Memento.mapper;

import Memento.dtos.InputDto.AdPlacementCreateDto;
import Memento.dtos.OutputDto.AdPlacementOutputDto;
import Memento.entities.AdPlacement;
import Memento.entities.Category;
import Memento.entities.Tag;

import java.util.Objects;

/**
 * Maps between AdPlacement domain entity and DTOs.
 * - Does not create objects; only fills the provided instances.
 */
public class AdPlacementMapper {

    /**
     * Fill an output DTO from the domain entity.
     *
     * @param ad  domain entity (required)
     * @param dto output dto to fill (required)
     */
    public static void domainToDto(AdPlacement ad, AdPlacementOutputDto dto) {
        Objects.requireNonNull(ad, "ad must not be null");
        Objects.requireNonNull(dto, "dto must not be null");

        dto.setId(ad.getId());
        dto.setTitle(ad.getTitle());
        dto.setTargetUrl(ad.getTargetUrl());
        dto.setMediaUrl(ad.getMediaUrl());
        dto.setStartAt(ad.getStartAt());
        dto.setEndAt(ad.getEndAt());
        dto.setAdvertiserName(ad.getAdvertiserName());
        dto.setVisibility(ad.getVisibility());
        dto.setStatus(ad.getStatus());

        dto.setCategoryId(ad.getCategory() != null ? ad.getCategory().getId() : null);
        dto.setTagId(ad.getTag() != null ? ad.getTag().getId() : null);
    }

    /**
     * Apply create DTO values into the domain entity.
     * Category and Tag must be resolved by the service layer.
     *
     * @param dto      input dto (required)
     * @param ad       target entity to fill (required)
     * @param category resolved category or null
     * @param tag      resolved tag or null
     */
    public static void dtoToDomainUpdate(AdPlacementCreateDto dto, AdPlacement ad,
                                   Category category, Tag tag) {
        Objects.requireNonNull(dto, "dto must not be null");
        Objects.requireNonNull(ad, "ad must not be null");

        ad.setTitle(dto.getTitle());
        ad.setTargetUrl(dto.getTargetUrl());
        ad.setMediaUrl(dto.getMediaUrl());
        ad.setStartAt(dto.getStartAt());
        ad.setEndAt(dto.getEndAt());
        ad.setAdvertiserName(dto.getAdvertiserName());
        ad.setCategory(category); // may be null
        ad.setTag(tag);           // may be null

        // Visibility and Status defaults come from the entity:
        // visibility = PUBLIC, status = PENDING. Do not override here.
    }

    public static void dtoToDomainForCreate(AdPlacementCreateDto dto, AdPlacement ad) {
        Objects.requireNonNull(dto, "dto must not be null");
        Objects.requireNonNull(ad, "ad must not be null");

        ad.setTitle(dto.getTitle());
        ad.setTargetUrl(dto.getTargetUrl());
        ad.setMediaUrl(dto.getMediaUrl());
        ad.setStartAt(dto.getStartAt());
        ad.setEndAt(dto.getEndAt());
        ad.setAdvertiserName(dto.getAdvertiserName());

        // category e tag resolvidos no service, não aqui
    }

}
