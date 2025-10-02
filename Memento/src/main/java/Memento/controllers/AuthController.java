package Memento.controllers;

import Memento.dtos.InputDto.UserCreateDto;
import Memento.dtos.InputDto.UserLoginDto;
import Memento.dtos.OutputDto.LoginOutputDto;
import Memento.dtos.OutputDto.UserMeDto;
import Memento.dtos.OutputDto.UserPublicDto;
import Memento.security.SecurityUserDetails;
import Memento.services.IAuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginOutputDto> login(@Valid @RequestBody UserLoginDto credentials) {
        return ResponseEntity.ok(authService.login(credentials));
    }

    @PostMapping("/register")
    public ResponseEntity<UserPublicDto> register(@Valid @RequestBody UserCreateDto dto) {
        return ResponseEntity.ok(authService.register(dto));
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserMeDto> getMe(@AuthenticationPrincipal SecurityUserDetails userDetails) {
        return ResponseEntity.ok(authService.getMe(userDetails.getUsername())); // username = email
    }
}
