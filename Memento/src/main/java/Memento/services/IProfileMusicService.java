package Memento.services;

import Memento.dtos.InputDto.ProfileMusicCreateDto;
import Memento.dtos.OutputDto.ProfileMusicOutputDto;

import java.util.List;

public interface IProfileMusicService {

    /**
     * Add a new profile music entry for a user.
     */
    ProfileMusicOutputDto addProfileMusic(String userEmail, ProfileMusicCreateDto dto);

    /**
     * Get the currently active profile music of a user.
     */
    ProfileMusicOutputDto getActiveProfileMusic(Long userId);

    /**
     * Get all profile music entries for a user.
     */
    List<ProfileMusicOutputDto> getAllProfileMusic(Long userId);

    /**
     * Deactivate a profile music entry (only the owner can do this).
     */
    void deactivateProfileMusic(Long id, String userEmail);
}

