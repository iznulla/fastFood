package com.test.fastFood.service.user;

import com.test.fastFood.dto.user.UserDto;
import com.test.fastFood.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserEntity> createUser(UserDto userDto);
    List<UserEntity> findAllUsers();
    Optional<UserEntity> getUserByUsername(String username);
    Optional<UserEntity> getUserById(Long id);
    Optional<UserEntity> updateUser(Long id, UserDto userDto);
    public void deleteUser(Long id);
}
