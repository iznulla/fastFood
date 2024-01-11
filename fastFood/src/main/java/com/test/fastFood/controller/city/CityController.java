package com.test.fastFood.controller.city;

import com.test.fastFood.dto.address.CityDto;
import com.test.fastFood.service.address.city.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/city")
@PreAuthorize("hasAnyAuthority({'CREATE_COUNTRY_AND_CITY', 'ALL'})")
public class CityController {
    private final CityService cityService;

    @GetMapping
    public ResponseEntity<List<CityDto>> getAllCities() {
        return new ResponseEntity<>(cityService.getAllCities().orElseThrow(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CityDto> createCity(@RequestBody CityDto cityDto) {
        return new ResponseEntity<>(cityService.createCity(cityDto).orElseThrow(), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityDto> getCityById(@PathVariable Long id) {
        return new ResponseEntity<>(cityService.getCityById(id).orElseThrow(), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CityDto> updateCity(@PathVariable Long id, @RequestBody CityDto cityDto) {
        return new ResponseEntity<>(cityService.updateCity(id, cityDto).orElseThrow(), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCountry(@PathVariable Long id) {
        cityService.deleteCityById(id);
        return ResponseEntity.noContent().build();
    }
}

