package Memento.security;

import Memento.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;

/**
 * Utility bean for handling JSON Web Tokens (JWT).
 * Generates, parses and validates tokens based on configuration properties.
 */
@Component
public final class JwtUtil {

    private final SecretKey signingKey;
    private final Duration accessTokenTtl;

    public JwtUtil(@Value("${security.jwt.secret}") String secret,
                   @Value("${security.jwt.ttl-minutes:30}") long ttlMinutes) {
        this.signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.accessTokenTtl = Duration.ofMinutes(ttlMinutes);
    }

    /**
     * Generates a JWT token for the given user.
     *
     * @param user the user for whom the token is being generated
     * @return a signed JWT token as a String
     */
    public String generateToken(User user) {
        Date now = new Date();
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("roles", user.getRoles().stream().map(r -> r.getName().name()).toList())
                .claim("userId", user.getId())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + accessTokenTtl.toMillis()))
                .signWith(signingKey)
                .compact();
    }


    /**
     * Parses and returns the claims (payload) from a given JWT token.
     *
     * @param token the JWT token to parse
     * @return the claims extracted from the token
     */
    public Claims getClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Validates a JWT token ensuring it is correctly signed and not expired.
     *
     * @param token the JWT token to validate
     * @return true if the token is valid, false otherwise
     */
    public boolean isTokenValid(String token) {
        try {
            return !isTokenExpired(getClaims(token));
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    boolean isTokenExpired(Claims claims) {
        Date expiration = claims.getExpiration();
        return expiration == null || expiration.before(new Date());
    }

    public Duration getAccessTokenTtl() {
        return accessTokenTtl;
    }

    public Date getExpiration(String token) {
        return getClaims(token).getExpiration();
    }

}
