package com.test.fastFood.configuration;

import com.test.fastFood.entity.user.UserEntity;
import com.test.fastFood.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ProjectConfig {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

//    @PostConstruct
//    private void initDefaultUsers() {
//        List<UserEntity> users = new ArrayList<>();
//        UserEntity admin = UserEntity.builder()
//                .username("admin")
//                .password(passwordEncoder.encode("admin"))
//                .role(null)
//                .isActive(true)
//                .build();
//        users.add(admin);
//        UserEntity user1 = UserEntity.builder()
//                .username("lolo")
//                .password(passwordEncoder.encode("123"))
//                .role(null)
//                .isActive(true)
//                .build();
//        users.add(user1);
//        UserEntity user2 = UserEntity.builder()
//                .username("bolo")
//                .password(passwordEncoder.encode("123"))
//                .role(null)
//                .isActive(true)
//                .build();
//        users.add(user2);
//        UserEntity user3 = UserEntity.builder()
//                .username("polo")
//                .password(passwordEncoder.encode("123"))
//                .role(null)
//                .isActive(true)
//                .build();
//        users.add(user3);
//        userRepository.saveAll(users);
//    }


    // environment
    // jar in docker compose
    // registration from email
    // country, region table
    // multi cafe
    // delivery from nearest cafe
}
