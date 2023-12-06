package com.test.fastFood.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_profile")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserEntity user;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;

    @OneToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
    @Column(name = "create_at")
    private Instant createAt;
}
