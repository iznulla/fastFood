package com.test.fastFood.controller;

import com.test.fastFood.dto.loginDTO.CreateUserDto;
import com.test.fastFood.entity.UserEntity;
import com.test.fastFood.service.userService.UserService;
import com.test.fastFood.service.userService.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/users")
public class AdminUserController {
    @Autowired private UserService userService;

    @PostMapping
    public ResponseEntity<CreateUserDto> createUser(@RequestBody CreateUserDto createUserDto) {
        userService.createUser(createUserDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }

    @GetMapping
    public List<UserEntity> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    public UserEntity getUserById(@PathVariable Long id) {
        return userService.getUserById(id).orElseThrow();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CreateUserDto> userEdit(@PathVariable Long id, @RequestBody CreateUserDto createUserDto) {
        Optional<UserEntity> user = userService.updateUser(id, createUserDto);
        return new ResponseEntity<>(UserServiceImpl.convertUserToDto(user.orElseThrow()), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
