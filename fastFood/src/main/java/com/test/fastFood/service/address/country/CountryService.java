package com.test.fastFood.service.address.country;

import com.test.fastFood.dto.address.CountryDto;
import com.test.fastFood.entity.address.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CountryService {
    Optional<CountryDto> createCountry(CountryDto countryDto);
    Optional<CountryDto> updateCountry(Long id, CountryDto countryDto);
    Optional<CountryDto> getCountryById(Long id);
    Optional<CountryDto> getCountryByName(String name);
    List<CountryDto> getAllCountries();
    void deleteCountryById(Long id);
}
