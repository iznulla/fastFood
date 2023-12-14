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
@PreAuthorize("hasAuthority('CREATE_RESTAURANT_AND_FILIAL')")
public class FilialController {
    private final RestaurantFilialService restaurantFilialService;

    @GetMapping
    public ResponseEntity<List<RestaurantFilialDto>> getAllRestaurantFilial() {
        return new ResponseEntity<>(restaurantFilialService.getAllRestaurantFilial(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RestaurantFilialDto> createRestaurantFilial(@RequestBody RestaurantFilialDto restaurantFilialDto) {
        restaurantFilialService.createRestaurantFilial(restaurantFilialDto).orElseThrow();
        return new ResponseEntity<>(restaurantFilialDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantFilialDto> getRestaurantFilialById(@PathVariable Long id) {
        return new ResponseEntity<>(restaurantFilialService.getRestaurantFilialById(id).orElseThrow(), HttpStatus.OK);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<RestaurantFilialDto> updateRestaurantFilial(@PathVariable Long id, @RequestBody RestaurantFilialDto restaurantFilialDto) {
        return new ResponseEntity<>(restaurantFilialService.updateRestaurantFilial(id, restaurantFilialDto).orElseThrow(), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRestaurantFilial(@PathVariable Long id) {
        restaurantFilialService.deleteRestaurantFilialById(id);
        return ResponseEntity.noContent().build();
    }
}
