package com.test.fastFood.service.userService;

import com.test.fastFood.dto.usetDTO.UserDto;
import com.test.fastFood.entity.Role;
import com.test.fastFood.entity.UserEntity;
import com.test.fastFood.entity.UserProfile;
import com.test.fastFood.repository.UserRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

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
        UserEntity user = repository.findById(id).orElseThrow();
        user.setUsername(userDto.getUsername());
        user.setPassword(user.getPassword());
        user.setRole(userDto.getRole());
        repository.save(user);
        return Optional.of(user);
    }

    @Override
    public void deleteUser(Long id) {
        UserEntity user = repository.findById(id).orElseThrow();
        repository.delete(user);
    }

}
