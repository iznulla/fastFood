package com.test.fastFood.service.userService;

import com.test.fastFood.dto.loginDTO.CreateUserDto;
import com.test.fastFood.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    CreateUserDto createUser(CreateUserDto createUserDto);
    List<UserEntity> findAllUsers();
    Optional<UserEntity> getUserByUsername(String username);
    Optional<UserEntity> getUserById(Long id);
    Optional<UserEntity> updateUser(Long id, CreateUserDto createUserDto);
    public void deleteUser(Long id);
}
