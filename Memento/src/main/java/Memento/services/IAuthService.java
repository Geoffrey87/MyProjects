package Memento.services;

import Memento.dtos.InputDto.UserCreateDto;
import Memento.dtos.InputDto.UserLoginDto;
import Memento.dtos.OutputDto.LoginOutputDto;
import Memento.dtos.OutputDto.UserMeDto;
import Memento.dtos.OutputDto.UserPublicDto;

public interface IAuthService {
    LoginOutputDto login(UserLoginDto credentials);
    UserPublicDto register(UserCreateDto dto);
    UserMeDto getMe(String email);
}

