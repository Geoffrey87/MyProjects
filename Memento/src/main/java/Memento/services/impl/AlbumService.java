package Memento.services.impl;

import Memento.dtos.InputDto.AlbumCreateDto;
import Memento.dtos.OutputDto.AlbumOutputDto;
import Memento.dtos.OutputDto.UserSummaryDto;
import Memento.entities.Album;
import Memento.entities.User;
import Memento.exception.ResourceNotFoundException;
import Memento.mapper.AlbumMapper;
import Memento.repositories.AlbumRepository;
import Memento.repositories.UserRepository;
import Memento.services.IAlbumService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AlbumService implements IAlbumService {

    private final AlbumRepository albumRepository;
    private final UserRepository userRepository;

    public AlbumService(AlbumRepository albumRepository, UserRepository userRepository) {
        this.albumRepository = albumRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AlbumOutputDto create(AlbumCreateDto dto, String email) {
        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email " + email));

        Album album = new Album();
        AlbumMapper.dtoToDomain(dto, album, owner);

        Album saved = albumRepository.save(album);

        AlbumOutputDto out = new AlbumOutputDto();
        UserSummaryDto ownerDto = new UserSummaryDto();
        AlbumMapper.domainToDto(saved, out, ownerDto);

        return out;
    }

    @Override
    public AlbumOutputDto update(Long id, AlbumCreateDto dto, String email) {
        Album album = getAlbumOrThrow(id);

        if (!album.getOwner().getEmail().equals(email)) {
            throw new IllegalStateException("You are not allowed to update this album");
        }

        AlbumMapper.dtoToDomain(dto, album, album.getOwner());
        Album updated = albumRepository.save(album);

        AlbumOutputDto out = new AlbumOutputDto();
        UserSummaryDto ownerDto = new UserSummaryDto();
        AlbumMapper.domainToDto(updated, out, ownerDto);

        return out;
    }

    @Override
    public void delete(Long id, String email) {
        Album album = getAlbumOrThrow(id);

        if (!album.getOwner().getEmail().equals(email)) {
            throw new IllegalStateException("You are not allowed to delete this album");
        }

        albumRepository.delete(album);
    }

    @Override
    public AlbumOutputDto getById(Long id) {
        Album album = getAlbumOrThrow(id);

        AlbumOutputDto out = new AlbumOutputDto();
        UserSummaryDto ownerDto = new UserSummaryDto();
        AlbumMapper.domainToDto(album, out, ownerDto);

        return out;
    }

    @Override
    public List<AlbumOutputDto> getByUser(Long userId) {
        List<Album> albums = albumRepository.findByOwnerId(userId);

        return albums.stream().map(album -> {
            AlbumOutputDto dto = new AlbumOutputDto();
            UserSummaryDto ownerDto = new UserSummaryDto();
            AlbumMapper.domainToDto(album, dto, ownerDto);
            return dto;
        }).collect(Collectors.toList());
    }

    // -----------------------------
    // Helpers
    // -----------------------------
    private Album getAlbumOrThrow(Long id) {
        return albumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Album not found with id " + id));
    }
}

