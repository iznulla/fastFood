package com.test.fastFood.controller.filial;

import com.test.fastFood.dto.filial.RestaurantFilialDto;
import com.test.fastFood.service.filial.RestaurantFilialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/filial")
@PreAuthorize("hasAnyAuthority({'CREATE_RESTAURANT_AND_FILIAL', 'ALL'})")
public class FilialController {
    private final RestaurantFilialService restaurantFilialService;

    @GetMapping
    public ResponseEntity<List<?>> getAllRestaurantFilial() {
        return new ResponseEntity<>(restaurantFilialService.getAllRestaurantFilial(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createRestaurantFilial(@RequestBody RestaurantFilialDto restaurantFilialDto) {;
        return new ResponseEntity<>(restaurantFilialService.createRestaurantFilial(restaurantFilialDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRestaurantFilialById(@PathVariable Long id) {
        return new ResponseEntity<>(restaurantFilialService.getRestaurantFilialById(id), HttpStatus.OK);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<?> updateRestaurantFilial(@PathVariable Long id, @RequestBody RestaurantFilialDto restaurantFilialDto) {
        return new ResponseEntity<>(restaurantFilialService.updateRestaurantFilial(id, restaurantFilialDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRestaurantFilial(@PathVariable Long id) {
        restaurantFilialService.deleteRestaurantFilialById(id);
        return ResponseEntity.noContent().build();
    }
}
