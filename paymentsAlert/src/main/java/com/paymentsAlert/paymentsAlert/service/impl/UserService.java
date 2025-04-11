package com.paymentsAlert.paymentsAlert.service.impl;

import com.paymentsAlert.paymentsAlert.JwtTokenProvider;
import com.paymentsAlert.paymentsAlert.dto.UserInputDto;
import com.paymentsAlert.paymentsAlert.dto.UserOutputDto;
import com.paymentsAlert.paymentsAlert.entity.User;
import com.paymentsAlert.paymentsAlert.mapper.UserMapper;
import com.paymentsAlert.paymentsAlert.repository.UserRepo;
import com.paymentsAlert.paymentsAlert.service.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUser {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public UserOutputDto registerUser(UserInputDto userInputDto) {
        if (userRepo.findByEmail(userInputDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = UserMapper.dtoToDomain(userInputDto, new User());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepo.save(user);
        return UserMapper.domainToDto(savedUser, new UserOutputDto());
    }

    public UserOutputDto loginUser(String email, String password) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        String token = jwtTokenProvider.generateToken(user.getEmail());

        UserOutputDto outputDto = UserMapper.domainToDto(user, new UserOutputDto());
        outputDto.setToken(token);

        return outputDto;
    }


    @Override
    public UserOutputDto getUserById(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return UserMapper.domainToDto(user, new UserOutputDto());
    }

    @Override
    public UserOutputDto getUserByEmail(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.domainToDto(user, new UserOutputDto());
    }

    @Override
    public UserOutputDto updateUser(Long id, UserInputDto userInputDto) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));


        if (!user.getEmail().equals(userInputDto.getEmail())) {
            userRepo.findByEmail(userInputDto.getEmail())
                    .ifPresent(existingUser -> {
                        throw new RuntimeException("Email already in use");
                    });
        }

        user.setUsername(userInputDto.getUsername());
        user.setEmail(userInputDto.getEmail());

        if (userInputDto.getPassword() != null && !userInputDto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userInputDto.getPassword()));
        }

        User updatedUser = userRepo.save(user);
        return UserMapper.domainToDto(updatedUser, new UserOutputDto());
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepo.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepo.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepo.findByEmail(email).isPresent();
    }
}