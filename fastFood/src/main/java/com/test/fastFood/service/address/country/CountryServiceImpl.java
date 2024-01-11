package com.test.fastFood.service.address.country;

import com.test.fastFood.dto.address.CountryDto;
import com.test.fastFood.entity.address.CountryEntity;
import com.test.fastFood.exception.ResourceNotFoundException;
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
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    @Override
    public Optional<CountryDto> createCountry(CountryDto countryDto) {
        CountryEntity country = CountryEntity.builder()
                .name(countryDto.getName())
                .build();
        countryRepository.save(country);
        log.info("Country created: {}", country.getName());
        return Optional.of(CountryDto.builder()
                .name(country.getName())
                .build());
    }

    @Override
    public Optional<CountryDto> updateCountry(Long id, CountryDto countryDto) {
        CountryEntity country = countryRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Not found country with id: " + id)
        );
        country.setName(countryDto.getName());
        countryRepository.save(country);
        log.info("Country updated: {}", country);
        return Optional.of(CountryDto.builder()
                .name(country.getName())
                .build());
    }

    @Override
    public Optional<CountryDto> getCountryById(Long id) {
        CountryEntity country = countryRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Not found country with id: " + id)
        );
        return Optional.of(CountryDto.builder()
                .name(country.getName())
                .build());
    }

    @Override
    public Optional<CountryDto> getCountryByName(String name) {
        CountryEntity country = countryRepository.findByName(name).orElseThrow(() ->
                new ResourceNotFoundException("Not found country with id: " + name)
        );
        return Optional.of(CountryDto.builder()
                .name(country.getName())
                .build());
    }

    @Override
    public List<CountryDto> getAllCountries() {
        return countryRepository.findAll().stream()
                .map(ConvertDtoUtils::convertCountryToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteCountryById(Long id) {
        CountryEntity country = countryRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Not found country with id: " + id)
        );
        countryRepository.deleteById(country.getId());
        log.info("Country deleted with id: {}", id);
    }
}
