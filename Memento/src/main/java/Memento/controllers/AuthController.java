package Memento.controllers;

import Memento.dtos.InputDto.UserCreateDto;
import Memento.dtos.InputDto.UserLoginDto;
import Memento.dtos.OutputDto.LoginOutputDto;
import Memento.dtos.OutputDto.UserMeDto;
import Memento.dtos.OutputDto.UserPublicDto;
import Memento.entities.User;
import Memento.security.JwtUtil;
import Memento.security.SecurityUserDetails;
import Memento.services.IUserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final IUserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, IUserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;

    }

    @PostMapping("/login")
    public ResponseEntity<LoginOutputDto> login(@Valid @RequestBody UserLoginDto credentials) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword())
        );

        SecurityUserDetails securityUserDetails = (SecurityUserDetails) authentication.getPrincipal();
        User user = securityUserDetails.getUser();

        String token = jwtUtil.generateToken(user);
        LocalDateTime expiresAt = jwtUtil.getExpiration(token)
                .toInstant().atZone(ZoneOffset.UTC).toLocalDateTime();

        LoginOutputDto response = new LoginOutputDto(
                token,
                "Bearer",
                expiresAt,
                null,
                null,
                toUserMeDto(user)
        );

        return ResponseEntity.ok(response);
    }


    private UserMeDto toUserMeDto(User user) {
        return new UserMeDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAvatarUrl(),
                user.getLocation(),
                user.getAge(),
                user.getCreatedAt()
        );
    }

    @PostMapping("/register")
    public ResponseEntity<UserPublicDto> register(@Valid @RequestBody UserCreateDto dto) {
        UserPublicDto user = userService.register(dto);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserMeDto> getMe(@AuthenticationPrincipal SecurityUserDetails userDetails) {
        UserMeDto me = userService.getMe(userDetails.getUsername()); // username = email
        return ResponseEntity.ok(me);
    }

}
