package Memento.services.impl;

import Memento.dtos.InputDto.ProfileMusicCreateDto;
import Memento.dtos.OutputDto.ProfileMusicOutputDto;
import Memento.entities.ProfileMusic;
import Memento.entities.User;
import Memento.exception.ForbiddenException;
import Memento.exception.ResourceNotFoundException;
import Memento.mapper.ProfileMusicMapper;
import Memento.repositories.ProfileMusicRepository;
import Memento.repositories.UserRepository;
import Memento.services.IProfileMusicService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProfileMusicService implements IProfileMusicService {

    private final ProfileMusicRepository profileMusicRepository;
    private final UserRepository userRepository;

    public ProfileMusicService(ProfileMusicRepository profileMusicRepository,
                               UserRepository userRepository) {
        this.profileMusicRepository = profileMusicRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ProfileMusicOutputDto addProfileMusic(String userEmail, ProfileMusicCreateDto dto) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));

        ProfileMusic profileMusic = new ProfileMusic();
        ProfileMusicMapper.dtoToDomain(dto, profileMusic, user);

        ProfileMusic saved = profileMusicRepository.save(profileMusic);

        return ProfileMusicMapper.domainToDto(saved, new ProfileMusicOutputDto());
    }

    @Override
    public ProfileMusicOutputDto getActiveProfileMusic(Long userId) {
        ProfileMusic profileMusic = profileMusicRepository
                .findFirstByUserIdAndIsActiveTrue(userId)
                .orElseThrow(() -> new ResourceNotFoundException("No active profile music found for user id: " + userId));

        return ProfileMusicMapper.domainToDto(profileMusic, new ProfileMusicOutputDto());
    }

    @Override
    public List<ProfileMusicOutputDto> getAllProfileMusic(Long userId) {
        List<ProfileMusic> musics = profileMusicRepository.findByUserId(userId);
        List<ProfileMusicOutputDto> dtos = new ArrayList<>();

        for (ProfileMusic music : musics) {
            dtos.add(ProfileMusicMapper.domainToDto(music, new ProfileMusicOutputDto()));
        }

        return dtos;
    }

    @Override
    public void deactivateProfileMusic(Long id, String userEmail) {
        ProfileMusic profileMusic = profileMusicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profile music not found with id: " + id));

        if (!profileMusic.getUser().getEmail().equals(userEmail)) {
            throw new ForbiddenException("You can only deactivate your own profile music.");
        }

        profileMusic.setIsActive(false);
        profileMusicRepository.save(profileMusic);
    }
}

