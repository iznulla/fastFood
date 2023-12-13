package com.test.fastFood.service.address.city;

import com.test.fastFood.dto.address.CityDto;
import com.test.fastFood.entity.address.CityEntity;
import com.test.fastFood.entity.address.CountryEntity;
import com.test.fastFood.exception.NotFoundException;
import com.test.fastFood.repository.CityRepository;
import com.test.fastFood.repository.CountryRepository;
import com.test.fastFood.utils.ConvertDtoUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    @Override
    public Optional<CityDto> createCity(CityDto cityDto) {
        CountryEntity country = countryRepository.findById(cityDto.getCountryId()).orElseThrow(() ->
                new NotFoundException("Not found country with id: " + cityDto.getCountryId()));
        CityEntity city = CityEntity.builder()
                .name(cityDto.getName())
                .build();
        city.setCountry(country);
        cityRepository.save(city);
        log.info("City created: {}", city.getName());
        return Optional.of(CityDto.builder()
                .name(city.getName())
                .build());
    }

    @Override
    public Optional<CityDto> updateCity(Long id, CityDto cityDto) {
        CityEntity city = cityRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Not found city with id: " + id));
        city.setName(cityDto.getName());
        cityRepository.save(city);
        log.info("City updated: {}", city.getName());
        return Optional.of(CityDto.builder()
                .name(city.getName())
                .build());
    }

    @Override
    public Optional<CityDto> getCityById(Long id) {
        return Optional.of(CityDto.builder()
                .name(cityRepository.findById(id).orElseThrow(() ->
                        new NotFoundException("Not found city with id: " + id)).getName())
                .build());
    }

    @Override
    public Optional<CityDto> getCityByName(String name) {
        return Optional.of(CityDto.builder()
                .name(cityRepository.findByName(name).orElseThrow(() ->
                        new NotFoundException("Not found city with id: " + name)).getName())
                .build());
    }

    @Override
    public Optional<List<CityDto>> getAllCities() {
        return Optional.of(cityRepository.findAll().stream()
                .map(ConvertDtoUtils::convertCityToDto).collect(Collectors.toList()));
    }

    @Override
    public void deleteCityById(Long id) {
        log.info("City deleted by id: {}", id);
        cityRepository.deleteById(id);
    }
}
