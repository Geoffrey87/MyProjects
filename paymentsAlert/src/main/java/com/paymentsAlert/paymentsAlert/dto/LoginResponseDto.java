package com.paymentsAlert.paymentsAlert.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for user login response.
 * The server responds with a token and the user ID.
 * the token is used for authentication in subsequent requests.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    private String token;
    private Long userId;
}
