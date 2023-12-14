package com.test.fastFood.controller.privilege;

import com.test.fastFood.dto.user.PrivilegeDto;
import com.test.fastFood.entity.user.Privilege;
import com.test.fastFood.service.user.role.PrivilegeService;
import com.test.fastFood.utils.ConvertDtoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/privilege")
@PreAuthorize("hasAuthority('ROLE_PRIVILEGE_SERVICE')")
public class PrivilegeController {
    private final PrivilegeService privilegeService;

    @PostMapping
    public void create(@RequestBody PrivilegeDto privilegeDto) {
        privilegeService.create(privilegeDto);
    }

    @GetMapping
    public List<Privilege> getAll() {
        return privilegeService.getAll();
    }

    @GetMapping("/{id}")
    public PrivilegeDto getById(@PathVariable Long id) {
        return ConvertDtoUtils.convertPrivilegeToDto(privilegeService.getById(id).orElseThrow());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        privilegeService.delete(id);
    }
}
