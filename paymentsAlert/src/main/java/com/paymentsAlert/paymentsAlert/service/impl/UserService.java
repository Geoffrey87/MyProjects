package com.paymentsAlert.paymentsAlert.service.impl;

import com.paymentsAlert.paymentsAlert.dto.UserInputDto;
import com.paymentsAlert.paymentsAlert.dto.UserOutputDto;
import com.paymentsAlert.paymentsAlert.entity.CustomUser;
import com.paymentsAlert.paymentsAlert.mapper.UserMapper;
import com.paymentsAlert.paymentsAlert.repository.UserRepo;
import com.paymentsAlert.paymentsAlert.service.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for user-related operations such as registration, retrieval,
 * updating, deletion, and authentication support.
 */
@Service
public class UserService implements IUser{

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Loads a user by their email to be used for authentication.
     *
     * @param email The email of the user.
     * @return A Spring Security {@link UserDetails} object with user credentials.
     * @throws UsernameNotFoundException If no user is found with the given email.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        CustomUser customUser = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return User
                .withUsername(customUser.getEmail())
                .password(customUser.getPassword())
                .build();
    }

    /**
     * Registers a new user in the system.
     *
     * @param userInputDto The input data for the new user.
     * @return A {@link UserOutputDto} with the registered user's information.
     * @throws RuntimeException If a user with the given email already exists.
     */
    @Override
    public UserOutputDto registerUser(UserInputDto userInputDto) {
        if (userRepo.findByEmail(userInputDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        CustomUser user = UserMapper.dtoToDomain(userInputDto, new CustomUser());
        user.setPassword(passwordEncoder.encode(userInputDto.getPassword()));

        CustomUser savedUser = userRepo.save(user);
        return UserMapper.domainToDto(savedUser, new UserOutputDto());
    }


    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user.
     * @return A {@link UserOutputDto} with the user's information.
     * @throws RuntimeException If the user is not found.
     */
    @Override
    public UserOutputDto getUserById(Long id) {
        CustomUser user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return UserMapper.domainToDto(user, new UserOutputDto());
    }

    /**
     * Retrieves a user by their email.
     *
     * @param email The email of the user.
     * @return A {@link UserOutputDto} with the user's information.
     * @throws RuntimeException If the user is not found.
     */
    @Override
    public UserOutputDto getUserByEmail(String email) {
        CustomUser user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.domainToDto(user, new UserOutputDto());
    }

    /**
     * Updates the user's information.
     *
     * @param id            The ID of the user to update.
     * @param userInputDto  The updated user data.
     * @return A {@link UserOutputDto} with the updated user information.
     * @throws RuntimeException If the user is not found or if the email is already used by another user.
     */
    @Override
    public UserOutputDto updateUser(Long id, UserInputDto userInputDto) {
        CustomUser user = userRepo.findById(id)
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

        CustomUser updatedUser = userRepo.save(user);
        return UserMapper.domainToDto(updatedUser, new UserOutputDto());
    }

    /**
     * Deletes a user from the system by their ID.
     *
     * @param id The ID of the user to delete.
     * @throws RuntimeException If the user is not found.
     */
    @Override
    public void deleteUser(Long id) {
        if (!userRepo.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepo.deleteById(id);
    }

    /**
     * Checks whether a user exists with the given email.
     *
     * @param email The email to check.
     * @return {@code true} if a user with the email exists, {@code false} otherwise.
     */
    @Override
    public boolean existsByEmail(String email) {
        return userRepo.findByEmail(email).isPresent();
    }
}