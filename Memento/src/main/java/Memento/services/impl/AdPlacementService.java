package Memento.services.impl;

import Memento.dtos.InputDto.AdPlacementCreateDto;
import Memento.dtos.OutputDto.AdPlacementOutputDto;
import Memento.entities.*;
import Memento.exception.ResourceNotFoundException;
import Memento.mapper.AdPlacementMapper;
import Memento.repositories.AdPlacementRepository;
import Memento.repositories.CategoryRepository;
import Memento.repositories.TagRepository;
import Memento.services.IAdPlacementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdPlacementService implements IAdPlacementService {

    private final AdPlacementRepository adPlacementRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    public AdPlacementService(
            AdPlacementRepository adPlacementRepository,
            CategoryRepository categoryRepository,
            TagRepository tagRepository
    ) {
        this.adPlacementRepository = adPlacementRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
    }

    // -----------------------------
    // Creation
    // -----------------------------
    @Override
    public AdPlacementOutputDto create(AdPlacementCreateDto dto) {
        AdPlacement ad = new AdPlacement();

        // Apply simple fields
        AdPlacementMapper.dtoToDomainForCreate(dto, ad);

        // Link category if provided
        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + dto.getCategoryId()));
            ad.setCategory(category);
        }

        // Link tag if provided
        if (dto.getTagId() != null) {
            Tag tag = tagRepository.findById(dto.getTagId())
                    .orElseThrow(() -> new ResourceNotFoundException("Tag not found with id " + dto.getTagId()));
            ad.setTag(tag);
        }

        // Newly created ads always start as PENDING
        ad.setStatus(AdStatus.PENDING);

        AdPlacement saved = adPlacementRepository.save(ad);
        AdPlacementOutputDto output = new AdPlacementOutputDto();
        AdPlacementMapper.domainToDto(saved, output);
        return output;
    }

    // -----------------------------
    // Moderation (admin only)
    // -----------------------------
    @Override
    public AdPlacementOutputDto approve(Long id) {
        AdPlacement ad = getById(id);
        ad.setStatus(AdStatus.APPROVED);
        return toOutputDto(ad);
    }

    @Override
    public AdPlacementOutputDto activate(Long id) {
        AdPlacement ad = getById(id);
        if (LocalDateTime.now().isBefore(ad.getStartAt()) ||
                LocalDateTime.now().isAfter(ad.getEndAt())) {
            throw new IllegalStateException("Ad cannot be activated outside its schedule");
        }
        ad.setStatus(AdStatus.ACTIVE);
        return toOutputDto(ad);
    }

    @Override
    public AdPlacementOutputDto expire(Long id) {
        AdPlacement ad = getById(id);
        ad.setStatus(AdStatus.EXPIRED);
        return toOutputDto(ad);
    }

    // -----------------------------
    // Retrieval (public endpoints)
    // -----------------------------
    @Override
    public List<AdPlacementOutputDto> getActiveAds() {
        return adPlacementRepository.findByStatus(AdStatus.ACTIVE).stream()
                .map(this::toOutputDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AdPlacementOutputDto> getActiveAdsByCategory(Long categoryId) {
        return adPlacementRepository.findByStatusAndCategoryId(AdStatus.ACTIVE, categoryId).stream()
                .map(this::toOutputDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AdPlacementOutputDto> getActiveAdsByTag(Long tagId) {
        return adPlacementRepository.findByStatusAndTagId(AdStatus.ACTIVE, tagId).stream()
                .map(this::toOutputDto)
                .collect(Collectors.toList());
    }

    // -----------------------------
    // Helpers
    // -----------------------------
    private AdPlacement getById(Long id) {
        return adPlacementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ad not found with id " + id));
    }

    private AdPlacementOutputDto toOutputDto(AdPlacement ad) {
        AdPlacementOutputDto dto = new AdPlacementOutputDto();
        AdPlacementMapper.domainToDto(ad, dto);
        return dto;
    }
}

