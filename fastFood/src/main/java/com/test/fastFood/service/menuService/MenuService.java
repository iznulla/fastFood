package com.test.fastFood.service.menuService;

import com.test.fastFood.dto.menuDTO.MenuDto;
import com.test.fastFood.entity.MenuEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface MenuService {
    void create(MenuDto menuDto);
    List<MenuEntity> findAllMenu();
    Optional<MenuEntity> findById(Long id);
    Optional<MenuEntity> findByName(String name);
    Optional<MenuEntity> update(Long id, MenuDto name);
    void delete(Long id);
}
