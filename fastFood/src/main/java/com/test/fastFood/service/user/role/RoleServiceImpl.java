package com.test.fastFood.service.user.role;

import com.test.fastFood.dto.user.PrivilegeDto;
import com.test.fastFood.dto.user.RoleDto;
import com.test.fastFood.entity.user.Privilege;
import com.test.fastFood.entity.user.RoleEntity;
import com.test.fastFood.entity.user.RolePrivilege;
import com.test.fastFood.exception.ResourceNotFoundException;
import com.test.fastFood.repository.PrivilegeRepository;
import com.test.fastFood.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;


    @Override
    public Optional<RoleEntity> getByName(String name) {
        return Optional.of(roleRepository.findByName(name).orElseThrow(() ->
                new ResourceNotFoundException("Not found role with name: " + name)));
    }

    @Override
    public Optional<RoleEntity> getById(Long id) {
        return Optional.of(roleRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Not found role with id: " + id)));
    }

    @Override
    public List<RoleEntity> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<RoleEntity> update(Long id, RoleDto roleDto) {
        RoleEntity role = roleRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Not found role with id: " + id));
        List<Privilege> rolePrivileges = role.getRolePrivileges().stream().map(RolePrivilege::getPrivilege).collect(Collectors.toList());
        try {
            if (roleDto.getPrivileges() != null) {
                for (PrivilegeDto p : roleDto.getPrivileges()) {
                    Privilege privilege = privilegeRepository.findByName(p.getName()).orElseThrow(() ->
                            new ResourceNotFoundException("Not found privilege"));
                    if (rolePrivileges.contains(privilege)) {
                        RolePrivilege rolePrivilege = new RolePrivilege();
                        rolePrivilege.setPrivilege(privilege);
                        rolePrivilege.setRole(role);
                    }
                }
            }
            role.setName(roleDto.getName());
            roleRepository.save(role);
            return Optional.of(role);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ResourceNotFoundException("Invalid Input data");
        }
    }

    @Override
    public Optional<RoleEntity> create(RoleDto roleDto) {
        RoleEntity role = new RoleEntity();
        try {
            if (roleDto.getPrivileges() != null) {
                for (PrivilegeDto p : roleDto.getPrivileges()) {
                    Privilege privilege = privilegeRepository.findByName(p.getName()).orElseThrow(() ->
                            new ResourceNotFoundException("Not found privilege"));
                    RolePrivilege rolePrivilege = new RolePrivilege();
                    rolePrivilege.setPrivilege(privilege);
                    rolePrivilege.setRole(role);
                }
            }
            role.setName(roleDto.getName());
            roleRepository.save(role);
            return Optional.of(role);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ResourceNotFoundException("Invalid Input data");
        }
    }

    @Override
    public void delete(Long id) {
        RoleEntity role = roleRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Not found role with id: " + id));
        roleRepository.deleteById(role.getId());
    }

    @Override
    public Optional<RoleEntity> deletePrivilege(Long roleId, Long privilegeId) {
        RoleEntity role = roleRepository.findById(roleId).orElseThrow(() ->
                new ResourceNotFoundException("Not found role with id: " + roleId));
        try {
            role.getRolePrivileges().removeIf(rolePrivilege -> rolePrivilege.getPrivilege().getId().equals(privilegeId));
            return Optional.of(roleRepository.save(role));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ResourceNotFoundException("Invalid Privilege id");
        }

    }
}
