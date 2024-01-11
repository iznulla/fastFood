package com.test.fastFood.service.user.role;

import com.test.fastFood.dto.user.PrivilegeDto;
import com.test.fastFood.entity.user.Privilege;

import java.util.List;
import java.util.Optional;

public interface PrivilegeService {
    Optional<PrivilegeDto> create(PrivilegeDto privilegeDto);
    Optional<PrivilegeDto> getById(Long id);
    Optional<PrivilegeDto> update(Long id, PrivilegeDto privilegeDto);
    List<PrivilegeDto> getAll();
    void delete(Long id);
}
