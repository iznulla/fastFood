package com.test.fastFood.service.user.role;

import com.test.fastFood.dto.user.PrivilegeDto;
import com.test.fastFood.dto.user.RoleDto;
import com.test.fastFood.entity.user.Privilege;
import com.test.fastFood.entity.user.RoleEntity;
import com.test.fastFood.entity.user.RolePrivilege;
import com.test.fastFood.exception.NotFoundException;
import com.test.fastFood.repository.PrivilegeRepository;
import com.test.fastFood.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;


    @Override
    public Optional<RoleEntity> getByName(String name) {
        return Optional.of(roleRepository.findByName(name).orElseThrow(() ->
                new NotFoundException("Not found role with name: " + name)));
    }

    @Override
    public Optional<RoleEntity> getById(Long id) {
        return Optional.of(roleRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Not found role with id: " + id)));
    }

    @Override
    public List<RoleEntity> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<RoleEntity> update(Long id, RoleDto roleDto) {
        RoleEntity role = roleRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Not found role with id: " + id));
        role.setName(roleDto.getName());
        roleRepository.save(role);
        return Optional.of(role);
    }

    @Override
    public Optional<RoleEntity> create(RoleDto roleDto) {
        RoleEntity role = new RoleEntity();
        List<RolePrivilege> rolePrivileges = new ArrayList<>();
        for (PrivilegeDto p : roleDto.getPrivileges()) {
            Privilege privilege = privilegeRepository.findByName(p.getName()).orElseThrow(() ->
                    new NotFoundException("Not found privilege with name: " + p.getName()));
            RolePrivilege rolePrivilege = new RolePrivilege();
            rolePrivilege.setPrivilege(privilege);
            rolePrivilege.setRole(role);
        }
        role.setName(roleDto.getName());
        roleRepository.save(role);
        return Optional.of(role);
    }

    @Override
    public void delete(Long id) {
        roleRepository.deleteById(id);
    }
}
