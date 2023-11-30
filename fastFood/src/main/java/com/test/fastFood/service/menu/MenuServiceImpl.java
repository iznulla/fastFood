package com.test.fastFood.service.menu;

import com.test.fastFood.dto.menu.MenuDto;
import com.test.fastFood.entity.MenuEntity;
import com.test.fastFood.exception.NotFoundException;
import com.test.fastFood.repository.MenuRepository;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Data
@Builder
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService{
    private final MenuRepository repository;

    @Override
    public void create(MenuDto menuDto) {
        MenuEntity menu = MenuEntity.builder()
                .name(menuDto.getName())
                .price(menuDto.getPrice())
                .createAt(Instant.now())
                .cookingTime(menuDto.getCookingTime())
                .build();
        log.warn("Created menu by name {}", menuDto.getName());
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
        MenuEntity menu  = repository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Menu with id %d not found", id)
        ));
        menu.setName(menuDto.getName());
        menu.setPrice(menuDto.getPrice());
        repository.save(menu);
        log.warn("Updated menu by id {}", id);
        return Optional.of(menu);
    }


    @Override
    public void delete(Long id) {
        MenuEntity menu = repository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Menu with id %d not found", id)
        ));
        log.warn("deleted menu by id {}", id);
        repository.delete(menu);
    }
}
