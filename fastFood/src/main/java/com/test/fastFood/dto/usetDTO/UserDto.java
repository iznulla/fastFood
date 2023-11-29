package com.test.fastFood.dto.usetDTO;

import com.test.fastFood.entity.Role;
import lombok.*;
import org.springframework.stereotype.Component;


@Data
@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String username;
    private String password;
    private Role role = Role.USER;
    private String name;
    private String surname;
    private String address;
    private Integer ordersCount = 0;

}
