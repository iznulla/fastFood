package com.test.fastFood.controller.userController;

import com.test.fastFood.dto.orderDTO.OrderDto;
import com.test.fastFood.dto.usetDTO.UserDto;
import com.test.fastFood.entity.UserEntity;
import com.test.fastFood.service.orderService.OrderService;
import com.test.fastFood.service.userService.UserService;
import com.test.fastFood.utils.ConvertDtoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        Optional<UserEntity> user = userService.createUser(userDto);
        return new ResponseEntity<>(ConvertDtoUtils.convertUserToDto(user.orElseThrow()), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'WAITER')")
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return new ResponseEntity<>(userService.findAllUsers().stream()
                .map(ConvertDtoUtils::convertUserToDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(ConvertDtoUtils.convertUserToDto(userService.getUserById(id).orElseThrow()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        Optional<UserEntity> user = userService.updateUser(id, userDto);
        return new ResponseEntity<>(ConvertDtoUtils.convertUserToDto(user.orElseThrow()), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<OrderDto>> getUserOrders(@PathVariable Long id){
        return new ResponseEntity<>(orderService.getOrdersByUser(id)
                .stream().map(ConvertDtoUtils::convertOrderToDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

}
