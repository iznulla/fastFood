package com.test.fastFood.service.privilege;

import com.test.fastFood.dto.user.PrivilegeDto;
import com.test.fastFood.entity.user.Privilege;
import com.test.fastFood.exception.ResourceNotFoundException;
import com.test.fastFood.repository.PrivilegeRepository;
import com.test.fastFood.service.user.role.PrivilegeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PrivilegeServiceTest {
    @Mock
    private PrivilegeRepository privilegeRepository;

    @InjectMocks
    private PrivilegeServiceImpl privilegeService;

    private Privilege privilege;
    private Privilege privilegeUpdated;
    private PrivilegeDto privilegeDto;
    private PrivilegeDto privilegeDtoUpdated;

    @BeforeEach
    public void setUp() {
        privilege = Privilege.builder()
                .id(1L)
                .name("Test")
                .build();

        privilegeUpdated = Privilege.builder()
                .id(1L)
                .name("Updated")
                .build();

        privilegeDto = PrivilegeDto.builder()
                .name("Test")
                .build();

        privilegeDtoUpdated = PrivilegeDto.builder()
                .name("Updated")
                .build();
    }

    @Test
    public void PrivilegeService_createPrivilege_ReturnPrivilege() {
        Assertions.assertThat(privilegeService.create(privilegeDto).orElseThrow().getName()).isEqualTo(privilege.getName());
    }

    @Test
    public void PrivilegeService_updatePrivilege_ReturnPrivilege() {
        when(privilegeRepository.findById(1L)).thenReturn(Optional.of(privilege));
        Assertions.assertThat(privilegeService.update(1L, privilegeDtoUpdated).orElseThrow().getName()).isEqualTo(privilegeUpdated.getName());
    }

    @Test
    public void PrivilegeService_getPrivilegeById_ReturnPrivilegeOptional() {
        when(privilegeRepository.findById(1L)).thenReturn(Optional.of(privilege));
        PrivilegeDto privilegeTest = privilegeService.getById(1L).orElseThrow(() -> new ResourceNotFoundException("Privilege with id: " + 1L + " not found"));
        assertAll(
                () -> Assertions.assertThat(privilegeTest).isNotNull(),
                () -> Assertions.assertThat(privilegeTest.getName()).isEqualTo(privilege.getName())
        );
    }

    @Test
    public void PrivilegeService_deletePrivilegeById_ReturnNon() {
        assertAll(() ->
                privilegeRepository.deleteById(1L));
    }

    @Test
    public void PrivilegeService_badId__ReturnThrow_ResourceNotFound() {
        assertAll(
                () -> Assertions.assertThatThrownBy(() -> {
                                    privilegeService.getById(1L).orElseThrow();
                                })
                                .isInstanceOf(ResourceNotFoundException.class)
                                .hasMessage("Privilege with id: 1 not found"),
                () -> Assertions.assertThatThrownBy(() ->
                                privilegeService.update(1L, privilegeDtoUpdated))
                        .isInstanceOf(ResourceNotFoundException.class)
                        .hasMessage("Privilege with id: 1 not found")
        );
    }
}
