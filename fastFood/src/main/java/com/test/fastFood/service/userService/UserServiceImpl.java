package com.test.fastFood.service.userService;

import com.test.fastFood.dto.loginDTO.CreateUserDto;
import com.test.fastFood.entity.Role;
import com.test.fastFood.entity.UserEntity;
import com.test.fastFood.repository.UserRepository;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Data
@Builder
public class UserServiceImpl implements UserService {
    @Autowired private UserRepository repository;

    @Override
    public CreateUserDto createUser(CreateUserDto createUserDto) {
        UserEntity user = UserEntity.builder()
                .username(createUserDto.getUsername())
                .password(createUserDto.getPassword())
                .role(Role.USER)
                .createAt(Instant.now())
                .build();
        repository.save(user);
        return null;
    }


    public static CreateUserDto convertUserToDto(UserEntity user) {
        return CreateUserDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
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
    public Optional<UserEntity> updateUser(Long id, CreateUserDto createUserDto) {
        UserEntity user  = repository.findById(id).orElseThrow();
        user.setUsername(createUserDto.getUsername());
        user.setPassword(createUserDto.getPassword());
        user.setRole(createUserDto.getRole());
        repository.save(user);
        return Optional.of(user);
    }

    @Override
    public void deleteUser(Long id) {
        UserEntity user = repository.findById(id).orElseThrow();
        repository.delete(user);
    }

}
