package com.test.fastFood.controller.country;

import com.test.fastFood.dto.address.CountryDto;
import com.test.fastFood.service.address.country.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/country")
@PreAuthorize("hasRole('ADMIN')")
public class CountryController {
    private final CountryService countryService;

    @GetMapping
    public ResponseEntity<List<CountryDto>> getAllCountries() {
        return new ResponseEntity<>(countryService.getAllCountries(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CountryDto> createCountry(@RequestBody CountryDto countryDto) {
        return new ResponseEntity<>(countryService.createCountry(countryDto).orElseThrow(), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryDto> getCountryById(@PathVariable Long id) {
        return new ResponseEntity<>(countryService.getCountryById(id).orElseThrow(), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CountryDto> updateCountry(@PathVariable Long id, @RequestBody CountryDto countryDto) {
        return new ResponseEntity<>(countryService.updateCountry(id, countryDto).orElseThrow(), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCountry(@PathVariable Long id) {
        countryService.deleteCountryById(id);
        return ResponseEntity.noContent().build();
    }
}
