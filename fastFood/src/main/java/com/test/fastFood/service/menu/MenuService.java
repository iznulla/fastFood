package com.test.fastFood.service.menu;

import com.test.fastFood.dto.menu.MenuDto;
import com.test.fastFood.entity.restaurant.MenuEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface MenuService {
    Optional<MenuDto> create(MenuDto menuDto);
    List<MenuDto> findAllMenu();
    Optional<MenuEntity> findById(Long id);
    Optional<MenuDto> findByName(String name);
    Optional<MenuDto> update(Long id, MenuDto name);
    void delete(Long id);
}
