package com.test.fastFood.service.role;

import com.test.fastFood.dto.user.RoleDto;
import com.test.fastFood.entity.user.RoleEntity;
import com.test.fastFood.exception.ResourceNotFoundException;
import com.test.fastFood.repository.RoleRepository;
import com.test.fastFood.service.user.role.RoleServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    private RoleEntity role;
    private RoleEntity roleUpdated;
    private RoleDto roleDto;
    private RoleDto roleUpdateDto;

    @BeforeEach
    public void setUp() {
        role = RoleEntity.builder()
                .id(1L)
                .name("Test")
                .build();

        roleUpdated = RoleEntity.builder()
                .id(1L)
                .name("Updated")
                .build();

        roleDto = RoleDto.builder()
                .name("Test")
                .build();

        roleUpdateDto = RoleDto.builder()
                .name("Updated")
                .build();
    }

    @Test
    public void PrivilegeService_createPrivilege_ReturnPrivilege() {
        Assertions.assertThat(roleService.create(roleDto).orElseThrow().getName()).isEqualTo(role.getName());
    }

    @Test
    public void PrivilegeService_updatePrivilege_ReturnPrivilege() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        Assertions.assertThat(roleService.update(1L, roleUpdateDto).orElseThrow().getName()).isEqualTo(roleUpdated.getName());
    }

    @Test
    public void PrivilegeService_getPrivilegeById_ReturnPrivilegeOptional() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        RoleEntity privilegeTest = roleService.getById(1L).orElseThrow(() -> new ResourceNotFoundException("Privilege with id: " + 1L + " not found"));
        assertAll(
                () -> Assertions.assertThat(privilegeTest).isNotNull(),
                () -> Assertions.assertThat(privilegeTest.getName()).isEqualTo(role.getName())
        );
    }

    @Test
    public void PrivilegeService_deletePrivilegeById_ReturnNon() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        assertAll(() ->
                roleService.delete(1L));
    }

    @Test
    public void PrivilegeService_badId__ReturnThrow_ResourceNotFound() {
        assertAll(
                () -> Assertions.assertThatThrownBy(() -> {
                            roleService.getById(1L).orElseThrow();
                        })
                        .isInstanceOf(ResourceNotFoundException.class)
                        .hasMessage("Not found role with id: 1"),
                () -> Assertions.assertThatThrownBy(() ->
                                roleService.update(1L, roleUpdateDto))
                        .isInstanceOf(ResourceNotFoundException.class)
                        .hasMessage("Not found role with id: 1")
        );
    }
}
