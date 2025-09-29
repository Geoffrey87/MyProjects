package Memento.services;

import Memento.dtos.InputDto.*;
import Memento.dtos.OutputDto.*;

public interface IUserService {

    UserPublicDto register(UserCreateDto dto);

    UserMeDto getMe(String email);

    UserMeDto updateProfile(String email, UserUpdateDto dto);

    UserPreferenceOutputDto updatePreferences(String email, UserPreferenceUpdateDto dto);

    void updateSettings(String email, UserSettingsUpdateDto dto);

    UserPublicDto getPublicProfile(Long id);
}


