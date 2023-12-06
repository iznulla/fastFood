package com.test.fastFood.configuration;

import com.test.fastFood.entity.MenuEntity;
import com.test.fastFood.enums.Role;
import com.test.fastFood.entity.UserEntity;
import com.test.fastFood.entity.UserProfile;
import com.test.fastFood.repository.MenuRepository;
import com.test.fastFood.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ProjectConfig {
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    private void initDefaultUsers() {
        List<UserEntity> users = new ArrayList<>();
        UserEntity admin = UserEntity.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .role(Role.ADMIN)
                .build();
//        UserProfile userProfileAdmin = UserProfile.builder()
//                .user(admin)
//                .createAt(Instant.now())
//                .name("Admin")
//                .surname("Admin")
//                .address()
//                .build();
//        admin.setUserProfile(userProfileAdmin);
        users.add(admin);
        UserEntity user1 = UserEntity.builder()
                .username("lolo")
                .password(passwordEncoder.encode("123"))
                .role(Role.USER)
                .build();
//        UserProfile userProfile1 = UserProfile.builder()
//                .user(user1)
//                .createAt(Instant.now())
//                .name("Freddy")
//                .surname("Barens")
//                .build();
//        user1.setUserProfile(userProfile1);
        users.add(user1);
        UserEntity user2 = UserEntity.builder()
                .username("bolo")
                .password(passwordEncoder.encode("123"))
                .role(Role.USER)
                .build();
//        UserProfile userProfile2 = UserProfile.builder()
//                .user(user2)
//                .createAt(Instant.now())
//                .name("John")
//                .surname("Doe")
//                .build();
//        user2.setUserProfile(userProfile2);
        users.add(user2);
        UserEntity user3 = UserEntity.builder()
                .username("polo")
                .password(passwordEncoder.encode("123"))
                .role(Role.USER)
                .build();
//        UserProfile userProfile3 = UserProfile.builder()
//                .user(user3)
//                .createAt(Instant.now())
//                .name("Bugi")
//                .surname("Man")
//                .build();
//        user3.setUserProfile(userProfile3);
        users.add(user3);
        userRepository.saveAll(users);


    }

//    @PostConstruct
//    private void initDefaultMenus() {
//        List<MenuEntity> menuEntities = new ArrayList<>();
//        MenuEntity menu1 = MenuEntity.builder()
//                .name("Osh")
//                .price(25000)
//                .cookingTime(4)
//                .createAt(Instant.now())
//                .build();
//        menuEntities.add(menu1);
//        MenuEntity menu2 = MenuEntity.builder()
//                .name("Samsa")
//                .price(5000)
//                .cookingTime(3)
//                .createAt(Instant.now())
//                .build();
//        menuEntities.add(menu2);
//        MenuEntity menu3 = MenuEntity.builder()
//                .name("Shashlik")
//                .price(18000)
//                .cookingTime(7)
//                .createAt(Instant.now())
//                .build();
//        menuEntities.add(menu3);
//        MenuEntity menu4 = MenuEntity.builder()
//                .name("Hot-Dog")
//                .price(15000)
//                .cookingTime(5)
//                .createAt(Instant.now())
//                .build();
//        menuEntities.add(menu4);
//
//        menuRepository.saveAll(menuEntities);
//
//    }

    // environment
    // registration from email
    // jar in docker compose
    // country, region table
    // multi cafe
    // delivery from nearest cafe
}
