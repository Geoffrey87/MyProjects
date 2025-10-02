package Memento.services.impl;

import Memento.dtos.InputDto.UserCreateDto;
import Memento.dtos.InputDto.UserLoginDto;
import Memento.dtos.OutputDto.LoginOutputDto;
import Memento.dtos.OutputDto.UserMeDto;
import Memento.dtos.OutputDto.UserPublicDto;
import Memento.entities.User;
import Memento.mapper.UserMapper;
import Memento.security.JwtUtil;
import Memento.security.SecurityUserDetails;
import Memento.services.IAuthService;
import Memento.services.IUserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@Transactional
public class AuthService implements IAuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final IUserService userService;

    public AuthService(AuthenticationManager authenticationManager, JwtUtil jwtUtil, IUserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
    public LoginOutputDto login(UserLoginDto credentials) {
        // Authenticate
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword())
        );

        SecurityUserDetails principal = (SecurityUserDetails) authentication.getPrincipal();
        User user = principal.getUser();

        // Token
        String token = jwtUtil.generateToken(user);
        LocalDateTime expiresAt = jwtUtil.getExpiration(token)
                .toInstant().atZone(ZoneOffset.UTC).toLocalDateTime();

        // Build response DTOs here (service responsibility)
        UserMeDto me = new UserMeDto();
        UserMapper.domainToMe(user, me);

        LoginOutputDto out = new LoginOutputDto();
        out.setToken(token);
        out.setTokenType("Bearer");
        out.setExpiresAt(expiresAt);
        out.setRefreshToken(null);
        out.setRefreshExpiresAt(null);
        out.setUser(me);

        return out;
    }

    @Override
    public UserPublicDto register(UserCreateDto dto) {
        return userService.register(dto);
    }

    @Override
    public UserMeDto getMe(String email) {
        return userService.getMe(email);
    }
}

