package com.paymentsAlert.paymentsAlert.mapper;

import com.paymentsAlert.paymentsAlert.dto.UserInputDto;
import com.paymentsAlert.paymentsAlert.dto.UserOutputDto;
import com.paymentsAlert.paymentsAlert.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static UserOutputDto domainToDto(User user, UserOutputDto userOutputDto) {
        userOutputDto.setUsername(user.getUsername());
        userOutputDto.setEmail(user.getEmail());
        return userOutputDto;
    }

    public static User dtoToDomain(UserInputDto userInputDto, User user) {
        user.setUsername(userInputDto.getUsername());
        user.setPassword(userInputDto.getPassword());
        user.setEmail(userInputDto.getEmail());
        return user;
    }

}
