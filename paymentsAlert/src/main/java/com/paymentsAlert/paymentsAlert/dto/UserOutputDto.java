package com.paymentsAlert.paymentsAlert.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for user output.
 * The server responds with the user ID, username, email and token.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserOutputDto {
    private long userId;

    private String username;

    private String email;

    private String token;
}
