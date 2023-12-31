package com.test.fastFood.service.secure;

import com.test.fastFood.security.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class LogoutManagerService {
    private final JwtProperties jwtProperties;
    public void logout() {
        jwtProperties.setExpiration(Instant.now());
    }
}
