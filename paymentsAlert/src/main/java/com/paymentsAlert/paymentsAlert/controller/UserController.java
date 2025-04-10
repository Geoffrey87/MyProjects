package com.paymentsAlert.paymentsAlert.controller;

import com.paymentsAlert.paymentsAlert.dto.UserInputDto;
import com.paymentsAlert.paymentsAlert.dto.UserOutputDto;
import com.paymentsAlert.paymentsAlert.service.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final IUser userService;

    @Autowired
    public UserController(IUser userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserOutputDto> registerUser(@Valid @RequestBody UserInputDto userInputDto) {
        UserOutputDto user = userService.registerUser(userInputDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserOutputDto> getUserById(@PathVariable Long id) {
        UserOutputDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserOutputDto> getUserByEmail(@PathVariable String email) {
        UserOutputDto user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserOutputDto> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserInputDto userInputDto) {
        UserOutputDto updated = userService.updateUser(id, userInputDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
