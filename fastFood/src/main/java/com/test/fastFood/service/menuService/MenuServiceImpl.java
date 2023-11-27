package com.test.fastFood.service.menuService;

import com.test.fastFood.dto.menuDTO.MenuDto;
import com.test.fastFood.entity.MenuEntity;
import com.test.fastFood.repository.MenuRepository;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Data
@Builder
public class MenuServiceImpl implements MenuService{
    @Autowired private MenuRepository repository;

    @Override
    public void create(MenuDto menuDto) {
        MenuEntity menu = MenuEntity.builder()
                .name(menuDto.getName())
                .price(menuDto.getPrice())
                .createAt(Instant.now())
                .build();
        repository.save(menu);
    }

    @Override
    public List<MenuEntity> findAllMenu() {
        return repository.findAll();
    }

    @Override
    public Optional<MenuEntity> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<MenuEntity> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Optional<MenuEntity> update(Long id, MenuDto menuDto) {
        MenuEntity menu  = repository.findById(id).orElseThrow();
        menu.setName(menuDto.getName());
        menu.setPrice(menuDto.getPrice());
        repository.save(menu);
        return Optional.of(menu);
    }


    @Override
    public void delete(Long id) {
        MenuEntity menu = repository.findById(id).orElseThrow();
        repository.delete(menu);
    }
}
