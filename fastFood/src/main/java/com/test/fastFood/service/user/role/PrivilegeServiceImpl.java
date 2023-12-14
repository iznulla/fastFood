package com.test.fastFood.service.user.role;

import com.test.fastFood.dto.user.PrivilegeDto;
import com.test.fastFood.entity.user.Privilege;
import com.test.fastFood.repository.PrivilegeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrivilegeServiceImpl implements PrivilegeService {
    private final PrivilegeRepository privilegeRepository;
    @Override
    public Optional<Privilege> create(PrivilegeDto privilegeDto) {
        return Optional.of(privilegeRepository.save(Privilege.builder().name(privilegeDto.getName()).build()));
    }

    @Override
    public Optional<Privilege> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Privilege> update(Long id, PrivilegeDto privilegeDto) {
        return Optional.empty();
    }

    @Override
    public List<Privilege> getAll() {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
