package com.test.fastFood.controller.role;

import com.test.fastFood.dto.user.RoleDto;
import com.test.fastFood.entity.user.RoleEntity;
import com.test.fastFood.entity.user.RolePrivilege;
import com.test.fastFood.exception.NotFoundException;
import com.test.fastFood.service.user.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
@PreAuthorize("hasAuthority('ROLE_PRIVILEGE_SERVICE')")
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    public void create(@RequestBody RoleDto roleDto) {
        roleService.create(roleDto);
    }

    @GetMapping
    public List<RoleEntity> getAll() {
        return roleService.getAll();
    }

    @GetMapping("/{id}")
    public List<RolePrivilege> getPrivilegeById(@PathVariable Long id) {
        RoleEntity role = roleService.getById(id).orElseThrow(() ->
                new NotFoundException("Not found role with id: " + id));
        return role.getRolePrivileges();
    }

    @PatchMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody RoleDto roleDto) {
        roleService.update(id, roleDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        roleService.delete(id);
    }
}
