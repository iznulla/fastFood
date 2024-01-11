package com.test.fastFood.service.user.role;

import com.test.fastFood.dto.user.PrivilegeDto;
import com.test.fastFood.entity.user.Privilege;
import com.test.fastFood.exception.ResourceNotFoundException;
import com.test.fastFood.repository.PrivilegeRepository;
import com.test.fastFood.utils.ConvertDtoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrivilegeServiceImpl implements PrivilegeService {
    private final PrivilegeRepository privilegeRepository;
    @Override
    public Optional<PrivilegeDto> create(PrivilegeDto privilegeDto) {
        Privilege privilege = Privilege.builder()
                .name(privilegeDto.getName())
                .build();
        privilegeRepository.save(privilege);
        return Optional.of(ConvertDtoUtils.convertPrivilegeToDto(privilege));
    }

    @Override
    public Optional<PrivilegeDto> getById(Long id) {
        Privilege privilege = privilegeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Privilege with id: "+ id + " not found")
        );
        return Optional.of(ConvertDtoUtils.convertPrivilegeToDto(privilege));
    }

    @Override
    public Optional<PrivilegeDto> update(Long id, PrivilegeDto privilegeDto) {
        Privilege privilege = privilegeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Privilege with id: "+ id + " not found")
        );
        privilege.setName(privilegeDto.getName());
        privilegeRepository.save(privilege);
        return Optional.of(ConvertDtoUtils.convertPrivilegeToDto(privilege));
    }

    @Override
    public List<PrivilegeDto> getAll() {
        return privilegeRepository.findAll().stream().map(ConvertDtoUtils::convertPrivilegeToDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        Privilege privilege = privilegeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Privilege with id: "+ id + " not found")
        );
        privilegeRepository.deleteById(privilege.getId());
    }
}
