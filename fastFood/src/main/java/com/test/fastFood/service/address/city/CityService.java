package com.test.fastFood.service.address.city;

import com.test.fastFood.dto.address.CityDto;
import com.test.fastFood.dto.address.CountryDto;

import java.util.List;
import java.util.Optional;

public interface CityService {
    Optional<CityDto> createCity(CityDto cityDto);
    Optional<CityDto> updateCity(Long id, CityDto cityDto);
    Optional<CityDto> getCityById(Long id);
    Optional<CityDto> getCityByName(String name);
    Optional<List<CityDto>> getAllCities();
    void deleteCityById(Long id);
}
