package com.test.fastFood.controller.restaurant;

import com.test.fastFood.dto.filial.RestaurantFilialDto;
import com.test.fastFood.dto.menu.MenuDto;
import com.test.fastFood.dto.restaurant.RestaurantDto;
import com.test.fastFood.service.restaurant.RestaurantService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantController {
    private final RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<List<RestaurantDto>> getAllRestaurant() {
        return new ResponseEntity<>(restaurantService.getAllRestaurant(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RestaurantDto> createRestaurant(@RequestBody RestaurantDto restaurantDto) {
        restaurantService.createRestaurant(restaurantDto).orElseThrow();
        return new ResponseEntity<>(restaurantDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDto> getRestaurantById(@PathVariable Long id) {
        return new ResponseEntity<>(restaurantService.getRestaurantById(id).orElseThrow(), HttpStatus.OK);
    }

    @GetMapping("/{id}/menu")
    public ResponseEntity<List<MenuDto>> getRestaurantMenu(@PathVariable Long id) {
        return new ResponseEntity<>(restaurantService.getMenusByRestaurantId(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/filial")
    public ResponseEntity<List<RestaurantFilialDto>> getRestaurantFilial(@PathVariable Long id) {
        return new ResponseEntity<>(restaurantService.getFilialsByRestaurant(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RestaurantDto> updateRestaurant(@PathVariable Long id, @RequestBody RestaurantDto restaurantDto) {
        return new ResponseEntity<>(restaurantService.updateRestaurant(id, restaurantDto).orElseThrow(), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurantById(id);
        return ResponseEntity.noContent().build();
    }
}
