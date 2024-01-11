package com.test.fastFood.service.restaurant;

import com.test.fastFood.dto.restaurant.RestaurantDto;
import com.test.fastFood.entity.restaurant.RestaurantEntity;
import com.test.fastFood.exception.ResourceNotFoundException;
import com.test.fastFood.repository.RestaurantRepository;
import com.test.fastFood.utils.ConvertDtoUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RestaurantServiceTest {
    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    private RestaurantEntity restaurant;
    private RestaurantDto restaurantDto;

    @BeforeEach
    public void setUp() {
        restaurant = RestaurantEntity.builder()
                .id(1L)
                .name("Test")
                .build();

        restaurantDto = ConvertDtoUtils.convertRestaurantToDto(restaurant);
    }

    @Test
    public void RestaurantService_createRestaurant_ReturnRestaurantDto() {
        Assertions.assertThat(restaurantService.createRestaurant(restaurantDto).orElseThrow().getName())
                .isEqualTo(restaurantDto.getName());
        assertAll(
                () -> Assertions.assertThat(restaurantService.createRestaurant(restaurantDto).orElseThrow()
                        .getName()).isEqualTo(restaurantDto.getName()),
                () -> Assertions.assertThat(restaurantService.createRestaurant(restaurantDto).orElseThrow()
                        .getName()).isEqualTo(restaurantDto.getName())
        );
    }

    @Test
    public void RestaurantService_updateRestaurant_ReturnRestaurantDto_andThrow() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        Assertions.assertThat(restaurantService.updateRestaurant(1L, restaurantDto).orElseThrow().getName())
                .isEqualTo(restaurantDto.getName());
        assertAll(
                () -> Assertions.assertThat(restaurantService.updateRestaurant(1L, restaurantDto).orElseThrow()
                        .getName()).isEqualTo(restaurantDto.getName()),
                () -> org.junit.jupiter.api.Assertions.assertThrows(
                        RuntimeException.class, () -> restaurantService.updateRestaurant(2L, restaurantDto))
        );
    }

    @Test
    public void RestaurantService_getRestaurantById_ReturnRestaurantDto() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        Assertions.assertThat(restaurantService.getRestaurantById(1L).orElseThrow().getName())
                .isEqualTo(restaurantDto.getName());
    }

    @Test
    public void RestaurantService_deleteRestaurantById_ReturnRestaurantDto() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        assertAll(() ->
                restaurantService.deleteRestaurantById(1L));
    }

    @Test
    public void RestaurantService_deleteRestaurantById_ReturnThrow() {
        assertAll(() ->
                Assertions.assertThatThrownBy(() -> restaurantService.deleteRestaurantById(1L))
                        .isInstanceOf(ResourceNotFoundException.class));
    }

}
