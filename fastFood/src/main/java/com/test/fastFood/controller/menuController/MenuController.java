package com.test.fastFood.controller.menuController;

import com.test.fastFood.dto.menuDTO.MenuDto;
import com.test.fastFood.service.menuService.MenuService;
import com.test.fastFood.utils.ConvertDtoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired private MenuService menuService;

    @PostMapping
    public ResponseEntity<MenuDto> create(@RequestBody MenuDto menuDto) {
        menuService.create(menuDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<MenuDto>> getAllMenu() {
        return new ResponseEntity<>(menuService.findAllMenu().stream()
                .map(ConvertDtoUtils::MenuEntityToDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuDto> getMenuById(@PathVariable Long id) {
        return new ResponseEntity<>(ConvertDtoUtils.MenuEntityToDto(menuService.findById(id).orElseThrow()), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MenuDto> update(@PathVariable Long id, @RequestBody MenuDto menuDto) {
        return new ResponseEntity<>(ConvertDtoUtils.MenuEntityToDto(menuService.update(id, menuDto).orElseThrow()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        menuService.delete(id);
    }
}
