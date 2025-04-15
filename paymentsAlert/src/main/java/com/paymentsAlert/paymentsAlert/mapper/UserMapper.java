package com.paymentsAlert.paymentsAlert.mapper;

import com.paymentsAlert.paymentsAlert.dto.UserInputDto;
import com.paymentsAlert.paymentsAlert.dto.UserOutputDto;
import com.paymentsAlert.paymentsAlert.entity.CustomUser;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static UserOutputDto domainToDto(CustomUser user, UserOutputDto userOutputDto) {
        userOutputDto.setUserId(user.getId());
        userOutputDto.setUsername(user.getUsername());
        userOutputDto.setEmail(user.getEmail());
        return userOutputDto;
    }

    public static CustomUser dtoToDomain(UserInputDto userInputDto, CustomUser user) {
        user.setUsername(userInputDto.getUsername());
        user.setPassword(userInputDto.getPassword());
        user.setEmail(userInputDto.getEmail());
        return user;
    }

}
