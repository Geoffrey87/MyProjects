package Memento.services.impl;

import Memento.dtos.InputDto.MemoryCapsuleCreateDto;
import Memento.dtos.OutputDto.MemoryCapsuleOutputDto;
import Memento.entities.*;
import Memento.exception.ForbiddenException;
import Memento.exception.ResourceNotFoundException;
import Memento.mapper.MemoryCapsuleMapper;
import Memento.repositories.*;
import Memento.services.IMemoryCapsuleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MemoryCapsuleService implements IMemoryCapsuleService {

    private final MemoryCapsuleRepository capsuleRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final MediaRepository mediaRepository;
    private final StoryRepository storyRepository;

    public MemoryCapsuleService(MemoryCapsuleRepository capsuleRepository,
                                UserRepository userRepository,
                                PostRepository postRepository,
                                MediaRepository mediaRepository,
                                StoryRepository storyRepository) {
        this.capsuleRepository = capsuleRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.mediaRepository = mediaRepository;
        this.storyRepository = storyRepository;
    }

    @Override
    public MemoryCapsuleOutputDto create(MemoryCapsuleCreateDto dto, String userEmail) {
        User owner = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));

        // Validation: only one target can be filled
        int filled = 0;
        if (dto.getPostId() != null) filled++;
        if (dto.getMediaId() != null) filled++;
        if (dto.getStoryId() != null) filled++;
        if (filled != 1) {
            throw new ForbiddenException("Exactly one target (post, media, or story) must be provided");
        }

        Post post = null;
        Media media = null;
        Story story = null;

        if (dto.getPostId() != null) {
            post = postRepository.findById(dto.getPostId())
                    .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + dto.getPostId()));
        }

        if (dto.getMediaId() != null) {
            media = mediaRepository.findById(dto.getMediaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Media not found with id " + dto.getMediaId()));
        }

        if (dto.getStoryId() != null) {
            story = storyRepository.findById(dto.getStoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Story not found with id " + dto.getStoryId()));
        }

        MemoryCapsule capsule = new MemoryCapsule();
        capsule = MemoryCapsuleMapper.dtoToDomain(dto, capsule, post, media, story);

        MemoryCapsule saved = capsuleRepository.save(capsule);
        MemoryCapsuleOutputDto output = new MemoryCapsuleOutputDto();
        return MemoryCapsuleMapper.domainToDto(saved, output);
    }


    @Override
    public MemoryCapsuleOutputDto getById(Long id) {
        MemoryCapsule capsule = capsuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Capsule not found with id " + id));

        MemoryCapsuleOutputDto dto = new MemoryCapsuleOutputDto();
        return MemoryCapsuleMapper.domainToDto(capsule, dto);
    }

    @Override
    public List<MemoryCapsuleOutputDto> getByUser(String userEmail) {
        User owner = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));

        List<MemoryCapsule> capsules = capsuleRepository
                .findByPostAuthorOrMediaOwnerOrStoryAuthor(owner, owner, owner);

        List<MemoryCapsuleOutputDto> dtos = new ArrayList<>();

        for (MemoryCapsule capsule : capsules) {
            MemoryCapsuleOutputDto dto = new MemoryCapsuleOutputDto();
            dtos.add(MemoryCapsuleMapper.domainToDto(capsule, dto));
        }

        return dtos;
    }

    @Override
    public MemoryCapsuleOutputDto publish(Long id, String userEmail) {
        MemoryCapsule capsule = capsuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Capsule not found with id " + id));

        if (LocalDateTime.now().isBefore(capsule.getUnlockAt())) {
            throw new IllegalStateException("Capsule cannot be published before unlockAt");
        }

        capsule.setIsPublished(true);
        MemoryCapsule updated = capsuleRepository.save(capsule);

        MemoryCapsuleOutputDto dto = new MemoryCapsuleOutputDto();
        return MemoryCapsuleMapper.domainToDto(updated, dto);
    }


    @Override
    public void delete(Long id, String userEmail) {
        MemoryCapsule capsule = capsuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Capsule not found with id " + id));

        // only owner (of post/media/story) can delete
        String ownerEmail = null;
        if (capsule.getPost() != null) ownerEmail = capsule.getPost().getAuthor().getEmail();
        if (capsule.getMedia() != null) ownerEmail = capsule.getMedia().getOwner().getEmail();
        if (capsule.getStory() != null) ownerEmail = capsule.getStory().getAuthor().getEmail();

        if (ownerEmail == null || !ownerEmail.equals(userEmail)) {
            throw new SecurityException("You can only delete your own capsules");
        }

        capsuleRepository.delete(capsule);
    }
}

