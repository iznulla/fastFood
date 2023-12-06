package com.test.fastFood.utils;

import com.test.fastFood.entity.restaurant.RestaurantFilial;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class DeliveryInfo {
    private final RestaurantFilial restaurantFilial;
    private final Double distance;
}
