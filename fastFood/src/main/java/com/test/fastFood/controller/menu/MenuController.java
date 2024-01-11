package com.test.fastFood.controller.menu;

import com.test.fastFood.dto.menu.MenuDto;
import com.test.fastFood.exception.ResourceNotFoundException;
import com.test.fastFood.service.menu.MenuService;
import com.test.fastFood.utils.ConvertDtoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @PreAuthorize("hasAnyAuthority({'CREATE', 'ALL'})")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody MenuDto menuDto) {
        return new ResponseEntity<>(menuService.create(menuDto), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<?> getAllMenu() {
        return new ResponseEntity<>(menuService.findAllMenu(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMenuById(@PathVariable Long id) {
        return new ResponseEntity<>(menuService.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority({'UPDATE', 'ALL'})")
    @PatchMapping("/{id}")
    public ResponseEntity<MenuDto> update(@PathVariable Long id, @RequestBody MenuDto menuDto) {
        return new ResponseEntity<>(menuService.update(id, menuDto).orElseThrow(), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority({'DELETE', 'ALL'})")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        menuService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
