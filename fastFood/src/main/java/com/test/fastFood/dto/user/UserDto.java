package com.test.fastFood.dto.user;

import com.test.fastFood.dto.address.AddressDto;
import com.test.fastFood.enums.Role;
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
    private AddressDto addressDto;
    private Integer ordersCount = 0;

}
