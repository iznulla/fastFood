package com.test.fastFood.configuration;

import com.test.fastFood.dto.address.AddressDto;
import com.test.fastFood.entity.address.Address;
import com.test.fastFood.entity.address.CityEntity;
import com.test.fastFood.entity.address.CountryEntity;
import com.test.fastFood.entity.user.*;
import com.test.fastFood.repository.*;
import com.test.fastFood.service.address.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.Instant;

@Configuration
@RequiredArgsConstructor
public class ProjectConfig {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;
    private final PasswordEncoder passwordEncoder;
    private final AddressService addressService;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;


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

        CountryEntity country = CountryEntity.builder()
                .name("ADMIN")
                .build();
        CityEntity city = CityEntity.builder()
                .name("ADMIN")
                .country(country)
                .build();
        countryRepository.save(country);
        cityRepository.save(city);
        Address address = addressService.createAddress(AddressDto.builder()
                .city("ADMIN")
                .country("ADMIN")
                .latitude(41.311)
                .longitude(69.240)
                .street("Test")
                .build()).orElseThrow();
        UserEntity admin = UserEntity.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .role(role)
                .isActive(true)
                .build();
        UserProfile profile = UserProfile.builder()
                .name("admin")
                .user(admin)
                .createAt(Instant.now())
                .surname("admin")
                .build();
        admin.setUserProfile(profile);
        profile.setAddress(address);
        userRepository.save(admin);


    }


    // environment
    // jar in docker compose
    // registration from email
    // country, region table
    // multi cafe
    // delivery from nearest cafe
}
