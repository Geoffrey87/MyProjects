package Memento.services.impl;

import Memento.dtos.InputDto.*;
import Memento.dtos.OutputDto.*;
import Memento.entities.*;
import Memento.entities.enums.RoleType;
import Memento.exception.ResourceNotFoundException;
import Memento.mapper.UserMapper;
import Memento.mapper.UserPreferenceMapper;
import Memento.mapper.UserSettingsMapper;
import Memento.repositories.*;
import Memento.services.IUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserSettingsRepository userSettingsRepository;
    private final UserPreferenceRepository userPreferenceRepository;
    private final RoleRepository roleRepository;
    private final TagRepository tagRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       UserSettingsRepository userSettingsRepository,
                       UserPreferenceRepository userPreferenceRepository,
                       RoleRepository roleRepository,
                       TagRepository tagRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userSettingsRepository = userSettingsRepository;
        this.userPreferenceRepository = userPreferenceRepository;
        this.roleRepository = roleRepository;
        this.tagRepository = tagRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserPublicDto register(UserCreateDto dto) {
        // Criar user
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));

        // Role default = USER
        Role roleUser = roleRepository.findByName(RoleType.USER)
                .orElseThrow(() -> new ResourceNotFoundException("Role USER not found"));
        user.setRoles(Set.of(roleUser));

        User saved = userRepository.save(user);

        // Criar settings default
        UserSettings settings = new UserSettings();
        settings.setUser(saved);
        userSettingsRepository.save(settings);

        // Criar preferences default
        UserPreference pref = new UserPreference();
        pref.setUser(saved);
        userPreferenceRepository.save(pref);

        // DTO de saída
        UserPublicDto output = new UserPublicDto();
        UserMapper.domainToPublic(saved, output);
        return output;
    }

    @Override
    public UserMeDto getMe(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        UserMeDto dto = new UserMeDto();
        UserMapper.domainToMe(user, dto);
        return dto;
    }

    @Override
    public UserMeDto updateProfile(String email, UserUpdateDto dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        if (dto.getName() != null) user.setName(dto.getName());
        if (dto.getLocation() != null) user.setLocation(dto.getLocation());
        if (dto.getAvatarUrl() != null) user.setAvatarUrl(dto.getAvatarUrl());
        if (dto.getAge() != null) user.setAge(dto.getAge());

        User updated = userRepository.save(user);

        UserMeDto output = new UserMeDto();
        UserMapper.domainToMe(updated, output);
        return output;
    }

    @Override
    public UserPreferenceOutputDto updatePreferences(String email, UserPreferenceUpdateDto dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        UserPreference preference = user.getPreference();
        List<Tag> tags = tagRepository.findAllById(dto.getPreferredTagIds());

        UserPreferenceMapper.dtoToDomain(dto, preference, tags);
        userPreferenceRepository.save(preference);

        UserPreferenceOutputDto output = new UserPreferenceOutputDto();
        UserPreferenceMapper.domainToDto(preference, output);
        return output;
    }

    @Override
    public void updateSettings(String email, UserSettingsUpdateDto dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        UserSettings settings = user.getSettings();
        UserSettingsMapper.applyUpdate(dto, settings);

        userSettingsRepository.save(settings);
    }

    @Override
    public UserPublicDto getPublicProfile(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        UserPublicDto dto = new UserPublicDto();
        UserMapper.domainToPublic(user, dto);
        return dto;
    }
}

