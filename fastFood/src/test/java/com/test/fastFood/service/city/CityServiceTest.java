package com.test.fastFood.service.city;

import com.test.fastFood.dto.address.CityDto;
import com.test.fastFood.entity.address.CityEntity;
import com.test.fastFood.entity.address.CountryEntity;
import com.test.fastFood.repository.CityRepository;
import com.test.fastFood.repository.CountryRepository;
import com.test.fastFood.service.address.city.CityServiceImpl;
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
public class CityServiceTest {
    @Mock
    private CityRepository cityRepository;
    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private CityServiceImpl cityService;

    private CityEntity city;
    private CountryEntity country;
    private CityDto updatedCityDto;
    private CityDto cityDtoTest;

    @BeforeEach
    public void setUp() {
        city = CityEntity.builder()
                .id(1L)
                .name("Samarkand")
                .build();

        country = CountryEntity.builder()
                .id(1L)
                .name("Uzbekistan")
                .build();

        updatedCityDto = CityDto.builder()
                .name("Tashkent")
                .build();
        cityDtoTest = CityDto.builder()
                .name("Samarkand")
                .countryId(1L).build();
    }

    @Test
    public void CityService_createCity_ReturnCityDto() {
        when(countryRepository.findById(1L)).thenReturn(Optional.of(country));
        CityDto cityDto = cityService.createCity(cityDtoTest).orElseThrow();
        Assertions.assertThat(cityDto.getName()).isNotNull();
        Assertions.assertThat(cityDto.getCountryId()).isEqualTo(cityDtoTest.getCountryId());
    }

    @Test
    public void CityService_getCityById_ReturnCityDto() {
        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));
        CityDto cityDto = cityService.getCityById(1L).orElseThrow();
        Assertions.assertThat(cityDto).isNotNull();
        Assertions.assertThat(cityDto.getName()).isEqualTo(city.getName());
    }

    @Test
    public void CityService_updateCity_ReturnCityDto() {
        when(cityRepository.save(Mockito.any(CityEntity.class))).thenReturn(city);
        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));
        CityDto updCityDto = cityService.updateCity(1L, updatedCityDto).orElseThrow();
        Assertions.assertThat(updCityDto.getName()).isNotNull();
        Assertions.assertThat(updatedCityDto.getCountryId()).isEqualTo(updCityDto.getCountryId());
    }

    @Test
    public void CityService_DeleteCityById_ReturnNon() {
        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));
        assertAll(() ->
            cityService.deleteCityById(1L));
    }

}
