package com.test.fastFood.controller.privilege;

import com.test.fastFood.dto.user.PrivilegeDto;
import com.test.fastFood.entity.user.Privilege;
import com.test.fastFood.service.user.role.PrivilegeService;
import com.test.fastFood.utils.ConvertDtoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/privilege")
@PreAuthorize("hasAnyAuthority({'ROLE_PRIVILEGE_SERVICE', 'ALL'})")
public class PrivilegeController {
    private final PrivilegeService privilegeService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PrivilegeDto privilegeDto) {
        return new ResponseEntity<>(privilegeService.create(privilegeDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody PrivilegeDto privilegeDto) {
        return new ResponseEntity<>(privilegeService.update(id, privilegeDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(privilegeService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return new ResponseEntity<>(privilegeService.getById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        privilegeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
