package com.test.fastFood.entity.user;

import com.test.fastFood.entity.user.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "role")
    private List<UserEntity> users = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<RolePrivilege> rolePrivileges = new ArrayList<>();
}
