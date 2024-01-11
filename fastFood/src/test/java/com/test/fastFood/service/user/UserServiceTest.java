package com.test.fastFood.service.user;

import com.test.fastFood.dto.address.AddressDto;
import com.test.fastFood.dto.user.RoleDto;
import com.test.fastFood.dto.user.UserDto;
import com.test.fastFood.entity.address.Address;
import com.test.fastFood.entity.address.CityEntity;
import com.test.fastFood.entity.address.CountryEntity;
import com.test.fastFood.entity.user.RoleEntity;
import com.test.fastFood.entity.user.UserEntity;
import com.test.fastFood.entity.user.UserProfile;
import com.test.fastFood.exception.ResourceNotFoundException;
import com.test.fastFood.repository.*;
import com.test.fastFood.service.address.AddressService;
import com.test.fastFood.service.email.EmailServiceImpl;
import com.test.fastFood.service.secure.EmailVerificationService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private AddressService addressService;
    @Mock
    private EmailServiceImpl emailService;
    @Mock
    private EmailVerificationService emailVerificationService;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private RoleEntity role;
    private Address address;
    private UserDto userDto;
    private UserDto updatedUserDto;
    private UserEntity user;

    @BeforeEach
    public void setUp() {

        AddressDto addressDto = AddressDto.builder()
                .street("Test")
                .city("Tashkent")
                .country("Uzbekistan")
                .latitude(1.0)
                .longitude(1.0)
                .build();


        CityEntity city = CityEntity.builder()
                .id(1L)
                .name("Samarkand")
                .build();

        CountryEntity country = CountryEntity.builder()
                .id(1L)
                .name("Uzbekistan")
                .build();
        address = Address.builder()
                .id(1L)
                .street("Test")
                .country(country)
                .city(city)
                .latitude(1.0)
                .longitude(1.0)
                .build();

        role = RoleEntity.builder()
                .id(1L)
                .name("Test")
                .build();

        RoleDto roleDto = RoleDto.builder()
                .name("Test")
                .build();

        userDto = UserDto.builder()
                .username("testUsername")
                .name("Test")
                .surname("Test")
                .password("Test")
                .role(roleDto)
                .address(addressDto)
                .build();

        updatedUserDto = UserDto.builder()
                .username("testUsernameUpdate")
                .name("Update")
                .surname("Update")
                .password("Update")
                .role(roleDto)
                .address(addressDto)
                .build();

        UserProfile userProfile = UserProfile.builder()
                .id(1L)
                .name("Test")
                .surname("TestSurname")
                .user(user)
                .createAt(Instant.now())
                .address(address)
                .build();

        user = UserEntity.builder()
                .userId(1L)
                .username("testUsername")
                .password(passwordEncoder.encode("testPassword"))
                .role(role)
                .isActive(true)
                .userId(1L)
                .userProfile(userProfile)
                .build();

    }

    @Test
    public void UserService_createUser_ReturnUserEntity() {
        when(addressService.createAddress(userDto.getAddress())).thenReturn(Optional.ofNullable(address));
        when(roleRepository.findByName("Test")).thenReturn(Optional.of(role));
        when(emailVerificationService.generateCode()).thenReturn("1234");
        Assertions.assertThat(userService.createUser(userDto).orElseThrow()).isNotNull();
        Assertions.assertThat(userService.createUser(userDto).orElseThrow().getUsername()).isEqualTo(userDto.getUsername());
    }

    @Test
    public void UserService_updateUser_ReturnUserEntity() {
        when(roleRepository.findByName(userDto.getRole().getName())).thenReturn(Optional.ofNullable(role));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        when(addressService.updateAddress(user.getUserProfile().getAddress().getId(), userDto.getAddress())).thenReturn(Optional.ofNullable(address));
        Assertions.assertThat(userService.updateUser(1L, updatedUserDto).orElseThrow()).isNotNull();
        Assertions.assertThat(userService.updateUser(1L, updatedUserDto).orElseThrow().getUsername()).isEqualTo(updatedUserDto.getUsername());
    }



    @Test
    public void UserService_getUserById_ReturnUserEntity() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        UserEntity userEntity = userService.getUserById(1L).orElseThrow();
        assertAll(
                () -> Assertions.assertThat(userEntity).isNotNull(),
                () -> Assertions.assertThat(userEntity.getUsername()).isEqualTo(user.getUsername()));
    }

    @Test
    public void UserService_deleteUserById_ReturnNon() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        assertAll(() ->
                userService.deleteUser(1L));
    }

    @Test
    public void UserService_badId__ReturnThrow_ResourceNotFound() {
        when(roleRepository.findByName(userDto.getRole().getName())).thenReturn(Optional.ofNullable(role));
        assertAll(
                () -> Assertions.assertThatThrownBy(() -> {
                            userService.deleteUser(1L);
                        })
                        .isInstanceOf(ResourceNotFoundException.class)
                        .hasMessage("User with id 1 not found"),
                () -> Assertions.assertThatThrownBy(() ->
                                userService.updateUser(1L, userDto))
                        .isInstanceOf(ResourceNotFoundException.class)
                        .hasMessage("User with id 1 not found")
        );
    }
}
