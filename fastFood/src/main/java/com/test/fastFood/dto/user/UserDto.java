package com.test.fastFood.dto.user;

import com.test.fastFood.dto.address.AddressDto;
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
    private RoleDto role;
    private String name;
    private String surname;
    private AddressDto address;
    private Integer ordersCount = 0;

}
