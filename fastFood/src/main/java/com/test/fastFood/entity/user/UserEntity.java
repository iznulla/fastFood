package com.test.fastFood.entity.user;

import com.test.fastFood.entity.order.OrderEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long userId;

    @Column(unique = true)
    private String username;
    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private RoleEntity role;
    @Column(columnDefinition = "boolean default false")
    private boolean isActive = false;

    @OneToOne(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, orphanRemoval = true)
    private UserProfile userProfile;

    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<OrderEntity> orders = new ArrayList<>();
}
