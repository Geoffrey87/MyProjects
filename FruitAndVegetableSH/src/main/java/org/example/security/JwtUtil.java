package com.paymentsAlert.paymentsAlert.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.User;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Utility class for handling JSON Web Tokens (JWT).
 * Provides methods to generate, validate, and parse JWT tokens
 * used for authenticating users in the application.
 */
public class JwtUtil {

    /**
     * Generates a JWT token for the given user.
     *
     * @param user the user for whom the token is being generated
     * @return a signed JWT token as a String
     */
    public static String generateToken(User user) {
        return Jwts
                .builder()
                .subject(user.getUsername())
                .expiration(new Date(System.currentTimeMillis() + 3000_000)) // 30 minutes
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Parses and returns the claims (payload) from a given JWT token.
     *
     * @param token the JWT token to parse
     * @return the claims extracted from the token
     */
    public static Claims getClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Validates a JWT token by checking its expiration date.
     *
     * @param token the JWT token to validate
     * @return true if the token is valid (not expired), false otherwise
     */
    public static boolean isTokenValid(String token) {
        return !isExpired(token);
    }

    /**
     * Checks whether the given JWT token is expired.
     *
     * @param token the JWT token to check
     * @return true if the token is expired, false otherwise
     */
    private static boolean isExpired(String token) {
        return getClaims(token)
                .getExpiration()
                .before(new Date());
    }

    /**
     * Returns the secret signing key used to sign and verify JWT tokens.
     *
     * @return a SecretKey for HMAC-SHA signing
     */
    private static SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode("1d8aeb26a4df8d8f09d9fb9f1f5a4327b755dff8e4fbbf7636b2a5a5b1f5b37d");
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
