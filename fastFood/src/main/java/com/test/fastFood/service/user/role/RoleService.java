package com.test.fastFood.service.user.role;

import com.test.fastFood.dto.user.RoleDto;
import com.test.fastFood.entity.user.RoleEntity;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Optional<RoleEntity> getByName(String name);
    Optional<RoleEntity> getById(Long id);
    List<RoleEntity> getAll();
    Optional<RoleEntity> update(Long id, RoleDto roleDto);
    Optional<RoleEntity> create(RoleDto roleDto);
    void delete(Long id);
    Optional<RoleEntity> deletePrivilege(Long roleId, Long privilegeId);

}
