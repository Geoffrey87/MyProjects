package Memento.controllers;

import Memento.dtos.InputDto.UserLoginDto;
import Memento.dtos.OutputDto.LoginOutputDto;
import Memento.dtos.OutputDto.UserMeDto;
import Memento.entities.User;
import Memento.security.JwtUtil;
import Memento.security.SecurityUserDetails;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginOutputDto> login(@Valid @RequestBody UserLoginDto credentials) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword())
        );

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof SecurityUserDetails securityUserDetails)) {
            throw new IllegalStateException("Unexpected principal type: " + principal.getClass());
        }

        User user = securityUserDetails.getUser();
        String token = jwtUtil.generateToken(user);
        LocalDateTime expiresAt = LocalDateTime.now(ZoneOffset.UTC).plus(jwtUtil.getAccessTokenTtl());

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
}
