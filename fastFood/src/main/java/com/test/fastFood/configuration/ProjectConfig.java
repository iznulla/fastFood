package com.test.fastFood.configuration;

import com.test.fastFood.entity.user.Privilege;
import com.test.fastFood.entity.user.RoleEntity;
import com.test.fastFood.entity.user.RolePrivilege;
import com.test.fastFood.entity.user.UserEntity;
import com.test.fastFood.repository.PrivilegeRepository;
import com.test.fastFood.repository.RoleRepository;
import com.test.fastFood.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class ProjectConfig {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;
    private final PasswordEncoder passwordEncoder;



    @PostConstruct
    @Transactional
    private void initDefaultUsers() {
        if (userRepository.findByUsername("admin").isPresent())
            return;
        Privilege privilege = Privilege.builder()
                .name("ALL")
                .build();
        privilegeRepository.save(privilege);
        RoleEntity role = RoleEntity.builder()
                .name("ADMIN")
                .build();

        RolePrivilege rolePrivilege = new RolePrivilege();
        rolePrivilege.setPrivilege(privilege);
        rolePrivilege.setRole(role);

        roleRepository.save(role);
        UserEntity admin = UserEntity.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .role(role)
                .isActive(true)
                .build();
        userRepository.save(admin);
    }


    // environment
    // jar in docker compose
    // registration from email
    // country, region table
    // multi cafe
    // delivery from nearest cafe
}
