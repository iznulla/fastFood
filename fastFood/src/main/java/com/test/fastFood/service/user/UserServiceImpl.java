package com.test.fastFood.service.user;

import com.test.fastFood.dto.user.UserDto;
import com.test.fastFood.entity.address.Address;
import com.test.fastFood.entity.user.RoleEntity;
import com.test.fastFood.entity.user.UserEntity;
import com.test.fastFood.entity.user.UserProfile;
import com.test.fastFood.exception.ResourceNotFoundException;
import com.test.fastFood.repository.RoleRepository;
import com.test.fastFood.repository.UserRepository;
import com.test.fastFood.service.address.AddressService;
import com.test.fastFood.service.email.EmailServiceImpl;
import com.test.fastFood.service.secure.EmailVerificationService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Data
@Builder
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AddressService addressService;
    private final EmailServiceImpl emailService;
    private final EmailVerificationService emailVerificationService;
    private final RoleRepository roleRepository;

    @Override
    public Optional<UserEntity> createUser(UserDto userDto) {
        RoleEntity role = roleRepository.findByName(userDto.getRole().getName()).orElseThrow(
                () -> new ResourceNotFoundException("Not found role with name: " + userDto.getRole().getName())
        );
        Address address = addressService.createAddress(userDto.getAddress()).orElseThrow();
        try {
            UserEntity user = UserEntity.builder()
                    .username(userDto.getUsername())
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .role(role)
                    .build();

            UserProfile profile = UserProfile.builder()
                    .user(user)
                    .name(userDto.getName())
                    .surname(userDto.getSurname())
                    .createAt(Instant.now()).build();
            profile.setAddress(address);
            user.setUserProfile(profile);
            repository.save(user);
            log.debug("Created user by name {}", userDto.getName());
            try {
                emailService.sendSimpleMessage(userDto.getUsername(), "Verify Code", emailVerificationService.generateCode());
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new ResourceNotFoundException("Invalid email address");
            }
            return Optional.of(user);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ResourceNotFoundException("Invalid input data " + e.getMessage());
        }
    }


    @Override
    public List<UserEntity> findAllUsers() {
        return repository.findAll();
    }

    @Override
    public Optional<UserEntity> getUserByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public Optional<UserEntity> getUserById(Long id) {
        if (repository.findById(id).isPresent()) {
            return repository.findById(id);
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserEntity> updateUser(Long id, UserDto userDto) {
        RoleEntity role = roleRepository.findByName(userDto.getRole().getName()).orElseThrow(()
                -> new ResourceNotFoundException("Not found role with name: " + userDto.getRole().getName()));
        UserEntity user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                String.format("User with id %s not found", id)
        ));
        user.setUsername(userDto.getUsername());
        user.setPassword(user.getPassword());
        user.setRole(role);
        Address address = addressService.updateAddress(user.getUserProfile().getAddress().getId(), userDto.getAddress()).orElseThrow(
                () -> new ResourceNotFoundException("Not found address with name: " + userDto.getAddress().getStreet())
        );
        user.getUserProfile().setName(userDto.getName());
        user.getUserProfile().setSurname(userDto.getSurname());
        user.getUserProfile().setAddress(address);
        repository.save(user);
        log.debug("Updated user by id {}", id);
        return Optional.of(user);
    }

    @Override
    public void deleteUser(Long id) {
        UserEntity user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                String.format("User with id %s not found", id)
        ));
        log.debug("deleted user by id {}", id);
        repository.delete(user);
    }

    @Override
    public boolean verification(Long id, String code) {
        UserEntity user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                String.format("User with id %s not found", id)
        ));
        if (user.isActive()) {
            return false;
        }
        Instant now = Instant.now();
        if (!now.isBefore(emailVerificationService.getExpiredDate())) {
            return false;
        }
        if (!code.equals(emailVerificationService.getVerifyCode())) {
            return false;
        }
        user.setActive(true);
        repository.save(user);
        log.debug("User verified by id {}", id);
        return true;
    }
}
