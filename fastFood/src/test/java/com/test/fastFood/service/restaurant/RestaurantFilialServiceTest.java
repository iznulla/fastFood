package com.test.fastFood.service.restaurant;

import com.test.fastFood.dto.address.AddressDto;
import com.test.fastFood.dto.filial.RestaurantFilialDto;
import com.test.fastFood.entity.address.Address;
import com.test.fastFood.entity.address.CityEntity;
import com.test.fastFood.entity.address.CountryEntity;
import com.test.fastFood.entity.restaurant.MenuEntity;
import com.test.fastFood.entity.restaurant.RestaurantEntity;
import com.test.fastFood.entity.restaurant.RestaurantFilial;
import com.test.fastFood.repository.RestaurantFilialRepository;
import com.test.fastFood.repository.RestaurantRepository;
import com.test.fastFood.service.address.AddressService;
import com.test.fastFood.service.filial.RestaurantFilialServiceImpl;
import com.test.fastFood.utils.ConvertDtoUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class RestaurantFilialServiceTest {
    @Mock
    private RestaurantFilialRepository restaurantFilialRepository;
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private AddressService addressService;
    @InjectMocks
    private RestaurantFilialServiceImpl restaurantFilialService;

    private RestaurantFilialDto restaurantFilialDto;
    private RestaurantFilialDto restaurantFilialDtoUpdated;
    private RestaurantFilial restaurantFilial;
    private Address address;
    private AddressDto addressDto;
    private RestaurantEntity restaurant;

    @BeforeEach
    public void setUp() {
        CountryEntity country = CountryEntity.builder()
                .id(1L)
                .name("Uzbekistan")
                .build();

        CityEntity city = CityEntity.builder()
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

        Address updatedAddress = Address.builder()
                .id(1L)
                .street("Updated")
                .country(country)
                .city(city)
                .latitude(1.0)
                .longitude(1.0)
                .build();

        addressDto = ConvertDtoUtils.convertAddressToDto(address);
        AddressDto addressDtoUpdated = ConvertDtoUtils.convertAddressToDto(updatedAddress);

        MenuEntity menu = MenuEntity.builder()
                .id(1L)
                .name("Test")
                .cookingTime(10)
                .price(10)
                .build();

        restaurant = RestaurantEntity.builder()
                .id(1L)
                .name("KFC")
                .build();

        restaurantFilial = RestaurantFilial.builder()
                .id(1L)
                .name("Test")
                .restaurant(restaurant)
                .address(address)
                .build();

        restaurantFilialDto = RestaurantFilialDto.builder()
                .name("Test")
                .restaurantId(1L)
                .address(addressDto)
                .build();

        restaurantFilialDtoUpdated = RestaurantFilialDto.builder()
                .name("Updated")
                .restaurantId(1L)
                .address(addressDtoUpdated)
                .build();
    }

    @Test
    public void RestaurantFilialService_createRestaurantFilial_ReturnRestaurantFilialOptionalDto() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(addressService.createAddress(addressDto)).thenReturn(Optional.of(address));
        RestaurantFilialDto restaurantFilialDtoTest = restaurantFilialService.createRestaurantFilial(
                restaurantFilialDto
        ).orElseThrow();
        assertAll(
                () -> Assertions.assertThat(restaurantFilialDtoTest).isNotNull(),
                () -> Assertions.assertThat(restaurantFilialDtoTest.getName()).isEqualTo(restaurantFilialDto.getName()),
                () -> Assertions.assertThat(restaurantFilialDtoTest.getAddress().getLatitude()).isEqualTo(restaurantFilialDto.getAddress()
                        .getLatitude()),
                () -> Assertions.assertThat(restaurantFilialDtoTest.getRestaurantId()).isEqualTo(restaurantFilialDto.getRestaurantId())
        );
    }

    @Test
    public void RestaurantFilialService_updateRestaurantFilial_ReturnRestaurantFilialOptionalDto() {
        when(restaurantFilialRepository.findById(1L)).thenReturn(Optional.of(restaurantFilial));
        when(addressService.updateAddress(restaurantFilial.getAddress().getId(), restaurantFilialDtoUpdated.getAddress()))
                .thenReturn(Optional.ofNullable(restaurantFilial.getAddress()));
        RestaurantFilialDto restaurantFilialDtoTest = restaurantFilialService.updateRestaurantFilial(
                1L, restaurantFilialDtoUpdated
        ).orElseThrow();
        assertAll(
                () -> Assertions.assertThat(restaurantFilialDtoTest).isNotNull(),
                () -> Assertions.assertThat(restaurantFilialDtoTest.getName()).isEqualTo(restaurantFilialDtoUpdated.getName()),
                () -> Assertions.assertThat(restaurantFilialDtoTest.getAddress().getLatitude()).isEqualTo(restaurantFilialDtoUpdated.getAddress()
                        .getLatitude()),
                () -> Assertions.assertThat(restaurantFilialDtoTest.getRestaurantId()).isEqualTo(restaurantFilialDtoUpdated.getRestaurantId())
        );
    }

    @Test
    public void RestaurantFilialService_deleteRestaurantFilial_ReturnNon() {
        when(restaurantFilialRepository.findById(1L)).thenReturn(Optional.of(restaurantFilial));
        restaurantFilialService.deleteRestaurantFilialById(1L);
    }

    @Test
    public void RestaurantFilialService_getRestaurantFilialById_ReturnRestaurantFilialOptionalDto() {
        when(restaurantFilialRepository.findById(1L)).thenReturn(Optional.of(restaurantFilial));
        RestaurantFilialDto restaurantFilialDtoTest = restaurantFilialService.getRestaurantFilialById(1L).orElseThrow();
        assertAll(
                () -> Assertions.assertThat(restaurantFilialDtoTest).isNotNull(),
                () -> Assertions.assertThat(restaurantFilialDtoTest.getName()).isEqualTo(restaurantFilialDto.getName()),
                () -> Assertions.assertThat(restaurantFilialDtoTest.getAddress().getLatitude()).isEqualTo(restaurantFilialDto.getAddress()
                        .getLatitude()),
                () -> Assertions.assertThat(restaurantFilialDtoTest.getRestaurantId()).isEqualTo(restaurantFilialDto.getRestaurantId())
        );
    }

    @Test
    public void RestaurantFilialService_getById_update_ReturnThrow() {
        assertAll(
                () -> Assertions.assertThatThrownBy(() -> restaurantFilialService.getRestaurantFilialById(1L)),
                () -> Assertions.assertThatThrownBy(() -> restaurantFilialService.updateRestaurantFilial(1L, restaurantFilialDtoUpdated)),
                () -> Assertions.assertThatThrownBy(() -> restaurantFilialService.deleteRestaurantFilialById(1L))
        );
    }

}
