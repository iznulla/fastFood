package com.test.fastFood.service.userService;

import com.test.fastFood.dto.usetDTO.UserDto;
import com.test.fastFood.entity.UserEntity;
import com.test.fastFood.entity.UserProfile;
import com.test.fastFood.exception.NotFoundException;
import com.test.fastFood.repository.UserRepository;
import com.test.fastFood.utils.SecurityUtils;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Data
@Builder
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<UserEntity> createUser(UserDto userDto) {
        UserEntity user = UserEntity.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(userDto.getRole())
                .build();
        UserProfile profile = UserProfile.builder()
                .user(user)
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .address(userDto.getAddress())
                .createAt(Instant.now()).build();
        user.setUserProfile(profile);
        repository.save(user);
        log.warn("Created user by name {}", userDto.getName());
        return Optional.of(user);
    }


    @Override
    public List<UserEntity> findAllUsers() {
        return repository.findAll();
    }

    @Override
    public Optional<UserEntity> getUserByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public Optional<UserEntity> getUserById(Long id) {
        if (repository.findById(id).isPresent()) {
            return repository.findById(id);
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserEntity> updateUser(Long id, UserDto userDto) {
        UserEntity user = repository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("User with id %s not found", id)
        ));
        user.setUsername(userDto.getUsername());
        user.setPassword(user.getPassword());
        user.setRole(userDto.getRole());
        repository.save(user);
        log.warn("Updated user by id {}", id);
        return Optional.of(user);
    }

    @Override
    public void deleteUser(Long id) {
        UserEntity user = repository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("User with id %s not found", id)
        ));
        log.warn("deleted user by id {}", id);
        repository.delete(user);
    }

}
