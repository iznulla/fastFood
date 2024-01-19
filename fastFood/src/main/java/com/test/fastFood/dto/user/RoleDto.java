package com.test.fastFood.dto.user;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    private String name;
    private List<PrivilegeDto> privileges;
}
