package Memento.services.impl;

import Memento.dtos.InputDto.MediaCreateDto;
import Memento.dtos.OutputDto.MediaOutputDto;
import Memento.dtos.OutputDto.TagOutputDto;
import Memento.dtos.OutputDto.UserSummaryDto;
import Memento.entities.Album;
import Memento.entities.Media;
import Memento.entities.Tag;
import Memento.entities.User;
import Memento.exception.ResourceNotFoundException;
import Memento.mapper.MediaMapper;
import Memento.repositories.AlbumRepository;
import Memento.repositories.MediaRepository;
import Memento.repositories.TagRepository;
import Memento.repositories.UserRepository;
import Memento.services.IMediaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class MediaService implements IMediaService {

    private final MediaRepository mediaRepository;
    private final UserRepository userRepository;
    private final AlbumRepository albumRepository;
    private final TagRepository tagRepository;

    public MediaService(MediaRepository mediaRepository,
                        UserRepository userRepository,
                        AlbumRepository albumRepository,
                        TagRepository tagRepository) {
        this.mediaRepository = mediaRepository;
        this.userRepository = userRepository;
        this.albumRepository = albumRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public MediaOutputDto upload(MediaCreateDto dto, String email) {
        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email " + email));

        Album album = null;
        if (dto.getAlbumId() != null) {
            album = albumRepository.findById(dto.getAlbumId())
                    .orElseThrow(() -> new ResourceNotFoundException("Album not found with id " + dto.getAlbumId()));
        }

        Media media = new Media();
        MediaMapper.dtoToDomain(dto, media, owner, album, Collections.emptySet());

        // Example: set size manually (in real world you'd get from file upload)
        media.setSizeMB(0.0);

        Media saved = mediaRepository.save(media);

        // Prepare DTOs for mapping
        MediaOutputDto out = new MediaOutputDto();
        UserSummaryDto ownerDto = new UserSummaryDto();
        List<TagOutputDto> tagDtos = new ArrayList<>();

        MediaMapper.domainToDto(saved, out, ownerDto, tagDtos);

        return out;
    }

    @Override
    public MediaOutputDto getById(Long id) {
        Media media = mediaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Media not found with id " + id));

        MediaOutputDto out = new MediaOutputDto();
        UserSummaryDto ownerDto = new UserSummaryDto();
        List<TagOutputDto> tagDtos = media.getTags().stream().map(tag -> {
            TagOutputDto t = new TagOutputDto();
            t.setId(tag.getId());
            t.setName(tag.getName());
            if (tag.getCategory() != null) {
                t.setCategoryId(tag.getCategory().getId());
            }
            return t;
        }).collect(Collectors.toList());

        MediaMapper.domainToDto(media, out, ownerDto, tagDtos);

        return out;
    }

    @Override
    public List<MediaOutputDto> getByUser(Long userId) {
        List<Media> mediaList = mediaRepository.findByOwnerId(userId);

        return mediaList.stream().map(media -> {
            MediaOutputDto dto = new MediaOutputDto();
            UserSummaryDto ownerDto = new UserSummaryDto();
            List<TagOutputDto> tagDtos = media.getTags().stream().map(tag -> {
                TagOutputDto t = new TagOutputDto();
                t.setId(tag.getId());
                t.setName(tag.getName());
                if (tag.getCategory() != null) {
                    t.setCategoryId(tag.getCategory().getId());
                }
                return t;
            }).collect(Collectors.toList());

            MediaMapper.domainToDto(media, dto, ownerDto, tagDtos);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id, String email) {
        Media media = mediaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Media not found with id " + id));

        if (!media.getOwner().getEmail().equals(email)) {
            throw new IllegalStateException("You are not allowed to delete this media");
        }

        mediaRepository.delete(media);
    }
}

