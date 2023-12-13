package com.test.fastFood.controller.menu;

import com.test.fastFood.dto.menu.MenuDto;
import com.test.fastFood.exception.NotFoundException;
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

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @PostMapping
    public ResponseEntity<MenuDto> create(@RequestBody MenuDto menuDto) {
        menuService.create(menuDto);
        return new ResponseEntity<>(menuDto, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<MenuDto>> getAllMenu() {
        return new ResponseEntity<>(menuService.findAllMenu().stream()
                .map(ConvertDtoUtils::MenuEntityToDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuDto> getMenuById(@PathVariable Long id) {
        return new ResponseEntity<>(ConvertDtoUtils.MenuEntityToDto(menuService.findById(id).orElseThrow(
                () -> new NotFoundException(
                        String.format("Menu with id %d not found", id)
                )
        )), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @PatchMapping("/{id}")
    public ResponseEntity<MenuDto> update(@PathVariable Long id, @RequestBody MenuDto menuDto) {
        return new ResponseEntity<>(ConvertDtoUtils.MenuEntityToDto(menuService.update(id, menuDto).orElseThrow()), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        menuService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
