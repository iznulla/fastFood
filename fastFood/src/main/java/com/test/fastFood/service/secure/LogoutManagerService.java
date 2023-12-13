package com.test.fastFood.service.secure;

import com.test.fastFood.security.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class LogoutManagerService {
    private final JwtProperties jwtProperties;
    public void logout() {
        System.out.println("AAAAAAKKKKKKKKKKKKKK");
        jwtProperties.setExpiration(Instant.now());
    }
}
