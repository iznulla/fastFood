package com.test.fastFood.controller.menuController;

import com.test.fastFood.dto.menuDTO.MenuDto;
import com.test.fastFood.entity.MenuEntity;
import com.test.fastFood.service.menuService.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/menu")
public class MenuController {
    @Autowired private MenuService menuService;

    @PostMapping
    public ResponseEntity<MenuDto> create(@RequestBody MenuDto menuDto) {
        menuService.create(menuDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public List<MenuEntity> getAllMenu() {
        return menuService.findAllMenu();
    }

    @GetMapping("/{id}")
    public MenuEntity getMenuById(@PathVariable Long id) {
        return menuService.findById(id).orElseThrow();
    }

    @PatchMapping("/{id}")
    public MenuEntity update(@PathVariable Long id, @RequestBody MenuDto menuDto) {
        return menuService.update(id, menuDto).orElseThrow();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        menuService.delete(id);
    }
}
