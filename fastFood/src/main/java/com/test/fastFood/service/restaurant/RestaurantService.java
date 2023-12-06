package com.test.fastFood.service.restaurant;

import com.test.fastFood.dto.menu.MenuDto;
import com.test.fastFood.dto.restaurant.RestaurantDto;
import com.test.fastFood.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantService {
    Optional<RestaurantDto> createRestaurant(RestaurantDto restaurantDto);
    Optional<RestaurantDto> updateRestaurant(Long id, RestaurantDto restaurantDto);
    Optional<RestaurantDto> getRestaurantById(Long id);
    List<RestaurantDto> getAllRestaurant();
    void deleteRestaurantById(Long id);
    List<MenuDto> getMenusByRestaurantId(Long id);
}
