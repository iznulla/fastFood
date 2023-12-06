package com.test.fastFood.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@Builder
@Data
public class JwtIssuer {
    private final JwtProperties jwtProperties;

    public String issue(Long userId, String username, List<String> roles) {
        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withExpiresAt(Instant.now().plus(Duration.of(1, ChronoUnit.HOURS)))
                .withClaim("u", username)
                .withClaim("a", roles)
                .sign(Algorithm.HMAC256(jwtProperties.getSecretKey()));
    }
}
