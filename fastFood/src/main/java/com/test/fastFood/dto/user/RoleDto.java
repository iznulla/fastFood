package com.test.fastFood.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class RoleDto {
    private String name;
    private List<PrivilegeDto> privileges;
}
