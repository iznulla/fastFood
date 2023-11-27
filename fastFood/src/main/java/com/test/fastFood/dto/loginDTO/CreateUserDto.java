package com.test.fastFood.dto.loginDTO;

import com.test.fastFood.entity.Role;
import lombok.*;
import org.springframework.stereotype.Component;


@Data
@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {
    private String username;
    private String password;
    private Role role = Role.USER;
}
