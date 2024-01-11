package com.test.fastFood.controller.role;

import com.test.fastFood.dto.user.RoleDto;
import com.test.fastFood.entity.user.RoleEntity;
import com.test.fastFood.exception.ResourceNotFoundException;
import com.test.fastFood.service.user.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
@PreAuthorize("hasAuthority('ROLE_PRIVILEGE_SERVICE')")
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody RoleDto roleDto) {
        return new ResponseEntity<>(roleService.create(roleDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(roleService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPrivilegeById(@PathVariable Long id) {
        RoleEntity role = roleService.getById(id).orElseThrow(() ->
                new ResourceNotFoundException("Not found role with id: " + id));
        return new ResponseEntity<>(role.getRolePrivileges(), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody RoleDto roleDto) {
        return new ResponseEntity<>(roleService.update(id, roleDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        roleService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}/privilege")
    public ResponseEntity<?> deletePrivilege(@PathVariable Long id, @RequestBody Long privilegeId) {
        return new ResponseEntity<>(roleService.deletePrivilege(id, privilegeId), HttpStatus.UPGRADE_REQUIRED);
    }
}
