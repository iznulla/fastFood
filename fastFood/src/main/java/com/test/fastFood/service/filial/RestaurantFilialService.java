package com.test.fastFood.service.filial;

import com.test.fastFood.dto.filial.RestaurantFilialDto;

import java.util.List;
import java.util.Optional;


public interface RestaurantFilialService {
    Optional<RestaurantFilialDto> createRestaurantFilial(RestaurantFilialDto restaurantfilialDto);
    Optional<RestaurantFilialDto> updateRestaurantFilial(Long id, RestaurantFilialDto restaurantfilialDto);
    Optional<RestaurantFilialDto> getRestaurantFilialById(Long id);
    List<RestaurantFilialDto> getAllRestaurantFilial();
    void deleteRestaurantFilialById(Long id);
}
