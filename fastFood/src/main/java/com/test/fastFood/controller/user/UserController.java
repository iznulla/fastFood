package com.test.fastFood.controller.user;

import com.test.fastFood.dto.order.OrderDto;
import com.test.fastFood.dto.user.UserDto;
import com.test.fastFood.dto.user.VerifyDto;
import com.test.fastFood.entity.user.UserEntity;
import com.test.fastFood.service.order.OrderService;
import com.test.fastFood.service.user.UserService;
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

    @PreAuthorize("hasAnyAuthority({'READ_USER', 'ALL'})")
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

    @PreAuthorize("hasAnyAuthority({'UPDATE_USER', 'ALL'})")
    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        Optional<UserEntity> user = userService.updateUser(id, userDto);
        return new ResponseEntity<>(ConvertDtoUtils.convertUserToDto(user.orElseThrow()), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority({'DELETE_USER', 'ALL'})")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<OrderDto>> getUserOrders(@PathVariable Long id){
        return new ResponseEntity<>(orderService.getOrdersByUser(id)
                .stream().map(ConvertDtoUtils::convertOrderToDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @PostMapping("/{id}/verify")
    public ResponseEntity<?> verifyUser(@PathVariable Long id, @RequestBody VerifyDto verifyDto){
        boolean verification = userService.verification(id, verifyDto.getCode());
        if (verification) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

}
