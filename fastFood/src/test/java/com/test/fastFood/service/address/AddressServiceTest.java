package com.test.fastFood.service.address;

import com.test.fastFood.dto.address.AddressDto;
import com.test.fastFood.entity.address.Address;
import com.test.fastFood.entity.address.CityEntity;
import com.test.fastFood.entity.address.CountryEntity;
import com.test.fastFood.exception.ResourceNotFoundException;
import com.test.fastFood.repository.AddressRepository;
import com.test.fastFood.repository.CityRepository;
import com.test.fastFood.repository.CountryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private CountryRepository countryRepository;
    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private AddressServiceImpl addressService;

    private Address address;
    private AddressDto addressDto;
    private AddressDto addressDtoUpdated;
    private CountryEntity country;
    private CityEntity city;

    @BeforeEach
    public void setUp() {
        addressDto = AddressDto.builder()
                .street("Test")
                .city("Tashkent")
                .country("Uzbekistan")
                .latitude(1.0)
                .longitude(1.0)
                .build();

        addressDtoUpdated = AddressDto.builder()
                .street("Updated")
                .country("Uzbekistan")
                .city("Samarkand")
                .latitude(2.0)
                .longitude(2.0)
                .build();

        country = CountryEntity.builder()
                .id(1L)
                .name("Uzbekistan")
                .build();

        city = CityEntity.builder()
                .id(1L)
                .country(country)
                .name("Tashkent")
                .build();
        address = Address.builder()
                .id(1L)
                .street("Test")
                .country(country)
                .city(city)
                .latitude(1.0)
                .longitude(1.0)
                .build();
    }

    @Test
    public void AddressService_createAddress_ReturnAddressOptionalEntity() {
        when(countryRepository.findByName("Uzbekistan")).thenReturn(Optional.of(country));
        when(cityRepository.findByName("Tashkent")).thenReturn(Optional.of(city));
        Address addressTest = addressService.createAddress(addressDto).orElseThrow(() -> new RuntimeException("Invalid input params"));
        assertAll(
                () -> Assertions.assertThat(addressTest).isNotNull(),
                () -> Assertions.assertThat(addressTest.getStreet()).isEqualTo(addressDto.getStreet()),
                () -> Assertions.assertThat(addressTest.getLatitude()).isEqualTo(addressDto.getLatitude()),
                () -> Assertions.assertThat(addressTest.getLongitude()).isEqualTo(addressDto.getLongitude())
        );
    }

    @Test
    public void AddressService_createAddress_ReturnAddressResourceNotFoundException() {
        when(countryRepository.findByName("Uzbekistan")).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> addressService.createAddress(addressDto)).isInstanceOf(ResourceNotFoundException.class);

    }

    @Test
    public void AddressService_updateAddress_ReturnAddressOptionalEntity() {
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        when(countryRepository.findByName("Uzbekistan")).thenReturn(Optional.of(country));
        when(cityRepository.findByName("Samarkand")).thenReturn(Optional.of(city));
        Address addressTest = addressService.updateAddress(1L, addressDtoUpdated).orElseThrow(() -> new RuntimeException("Invalid input params"));
        assertAll(
                () -> Assertions.assertThat(addressTest).isNotNull(),
                () -> Assertions.assertThat(addressTest.getStreet()).isEqualTo(addressDtoUpdated.getStreet()),
                () -> Assertions.assertThat(addressTest.getLatitude()).isEqualTo(addressDtoUpdated.getLatitude()),
                () -> Assertions.assertThat(addressTest.getLongitude()).isEqualTo(addressDtoUpdated.getLongitude())
        );
    }

    @Test
    public void AddressService_getAddressById_ReturnAddressOptionalDto() {
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        AddressDto addressTest = addressService.getAddressById(1L).orElseThrow(() -> new RuntimeException("Address not found"));
        assertAll(
                () -> Assertions.assertThat(addressTest).isNotNull(),
                () -> Assertions.assertThat(addressTest.getStreet()).isEqualTo(address.getStreet()),
                () -> Assertions.assertThat(addressTest.getLatitude()).isEqualTo(address.getLatitude()),
                () -> Assertions.assertThat(addressTest.getLongitude()).isEqualTo(address.getLongitude()),
                () -> Assertions.assertThat(addressTest.getCity()).isEqualTo(address.getCity().getName()),
                () -> Assertions.assertThat(addressTest.getCountry()).isEqualTo(address.getCountry().getName())
        );
    }

    @Test
    public void AddressService_deleteAddressById_ReturnNon() {
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        assertAll(() ->
                addressService.deleteAddressById(1L));
    }

    @Test
    public void AddressService_badId_badCity_badCountry_ReturnThrow_ResourceNotFound() {
        assertAll(() ->
                Assertions.assertThatThrownBy(() -> {
                            addressService.getAddressById(1L);
                        })
                        .isInstanceOf(ResourceNotFoundException.class)
                        .hasMessage("Address with id: 1 not found"),
                () -> Assertions.assertThatThrownBy(() ->
                                addressService.updateAddress(1L, addressDto))
                        .isInstanceOf(ResourceNotFoundException.class)
                        .hasMessage("Address with id: 1 not found")
        );

        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        Assertions.assertThatThrownBy(() ->
                                addressService.updateAddress(1L, addressDto))
                        .isInstanceOf(ResourceNotFoundException.class)
                        .hasMessage("Country with name: Uzbekistan not found");

        when(countryRepository.findByName("Uzbekistan")).thenReturn(Optional.of(country));
        Assertions.assertThatThrownBy(() ->
                        addressService.updateAddress(1L, addressDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("City with name: Tashkent not found");
    }


}
