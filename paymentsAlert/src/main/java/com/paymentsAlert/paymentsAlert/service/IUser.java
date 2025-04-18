package com.paymentsAlert.paymentsAlert.service;

import com.paymentsAlert.paymentsAlert.dto.UserInputDto;
import com.paymentsAlert.paymentsAlert.dto.UserOutputDto;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface IUser extends UserDetailsService {

    UserOutputDto registerUser(UserInputDto userInputDto);    UserOutputDto getUserById(Long id);


    UserOutputDto getUserByEmail(String email);

    UserOutputDto updateUser(Long id, UserInputDto userInputDto);

    void deleteUser(Long id);

    boolean existsByEmail(String email);
}
