package com.test.fastFood.entity.user;

import com.test.fastFood.dto.user.PrivilegeDto;
import com.test.fastFood.dto.user.RoleDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RolePrivilege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "privilege_id")
    private Privilege privilege;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    public PrivilegeDto getPrivilegeDto() {
        return PrivilegeDto.builder()
                .name(privilege.getName())
                .build();
    }

    public RoleDto getRole() {
        return RoleDto.builder()
                .name(role.getName())
                .build();
    }

    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
        privilege.getRolePrivileges().add(this);
    }

    public void setRole(RoleEntity role) {
        this.role = role;
        role.getRolePrivileges().add(this);
    }
}
