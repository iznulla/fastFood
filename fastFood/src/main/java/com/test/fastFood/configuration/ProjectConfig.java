package com.test.fastFood.configuration;

import com.test.fastFood.entity.Role;
import com.test.fastFood.entity.UserEntity;
import com.test.fastFood.entity.UserProfile;
import com.test.fastFood.service.userService.UserService;
import com.test.fastFood.utils.ConvertDtoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class ProjectConfig {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    private void initDefaultAdmin() {
        Optional<UserEntity> user = userService.getUserById(1L);
        if (user.isEmpty()) {
            UserEntity admin = UserEntity.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .role(Role.ADMIN)
                    .userProfile(UserProfile.builder().build())
                    .build();
            userService.createUser(ConvertDtoUtils.convertUserToDto(admin));
        }
    }
}
