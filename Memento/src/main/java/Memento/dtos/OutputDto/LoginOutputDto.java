package Memento.dtos.OutputDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginOutputDto {
    private String token;
    private String tokenType; // e.g. "Bearer"
    private LocalDateTime expiresAt; // optional: fill if you compute expiry
    private String refreshToken; // optional: if using refresh flow
    private LocalDateTime refreshExpiresAt; // optional: refresh token expiry
    private UserMeDto user; // current authenticated user info
}
