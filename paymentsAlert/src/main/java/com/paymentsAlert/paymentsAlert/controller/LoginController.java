package com.paymentsAlert.paymentsAlert.controller;

import com.paymentsAlert.paymentsAlert.dto.LoginDto;
import com.paymentsAlert.paymentsAlert.dto.LoginResponseDto;
import com.paymentsAlert.paymentsAlert.entity.CustomUser;
import com.paymentsAlert.paymentsAlert.repository.UserRepo;
import com.paymentsAlert.paymentsAlert.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final AuthenticationManager manager;
    private final UserRepo userRepo;

    @Autowired
    public LoginController(AuthenticationManager manager, UserRepo userRepo) {
        this.manager = manager;
        this.userRepo = userRepo;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(),
                loginDto.getPassword()
        );

        Authentication authentication = manager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUser user = userRepo.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String jwtToken = JwtUtil.generateToken((User)authentication.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDto(jwtToken, user.getId()));
    }
}
