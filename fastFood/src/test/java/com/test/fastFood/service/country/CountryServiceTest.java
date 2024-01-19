package com.test.fastFood.service.country;

import com.test.fastFood.dto.address.CountryDto;
import com.test.fastFood.entity.address.CountryEntity;
import com.test.fastFood.repository.CountryRepository;
import com.test.fastFood.service.address.country.CountryServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CountryServiceTest {
    @Mock
    private CountryRepository countryRepository;
    @InjectMocks
    private CountryServiceImpl countryService;
    private CountryEntity country;
    private CountryDto countryDto;
    private CountryDto updatedCountryDto;

    @BeforeEach
    public void setUp() {
        country = CountryEntity.builder()
                .id(1L)
                .name("Uzbekistan")
                .build();
        countryDto = CountryDto.builder()
                .name("Uzbekistan")
                .build();
        updatedCountryDto = CountryDto.builder()
                .name("Russia")
                .build();
    }

    @Test
    public void CountryService_createCountry_ReturnCountryDto() {
        CountryDto countryDtoTest = countryService.createCountry(countryDto).orElseThrow(() -> new RuntimeException("Invalid input params"));
        Assertions.assertThat(countryDtoTest).isNotNull();
        Assertions.assertThat(countryDtoTest.getName()).isEqualTo(countryDto.getName());
    }

    @Test
    public void CountryService_getCountryById_ReturnCountryDto() {
        when(countryRepository.findById(1L)).thenReturn(Optional.of(country));
        CountryDto countryDtoTest = countryService.getCountryById(1L).orElseThrow();
        Assertions.assertThat(countryDtoTest).isNotNull();
        Assertions.assertThat(countryDtoTest.getName()).isEqualTo(country.getName());
    }

    @Test
    public void CountryService_updateCountry_ReturnCountryDto() {
        when(countryRepository.save(Mockito.any(CountryEntity.class))).thenReturn(country);
        when(countryRepository.findById(1L)).thenReturn(Optional.of(country));
        CountryDto updatedCountryDtoTest = countryService.updateCountry(1L, updatedCountryDto).orElseThrow();
        Assertions.assertThat(updatedCountryDto).isNotNull();
        Assertions.assertThat(updatedCountryDtoTest.getName()).isEqualTo(updatedCountryDto.getName());
    }

    @Test
    public void CountryService_DeleteCountryById_ReturnNon() {
        when(countryRepository.findById(1L)).thenReturn(Optional.of(country));
        assertAll(() ->
                countryService.deleteCountryById(1L));
    }
}
