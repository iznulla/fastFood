package com.test.fastFood.dto.restaurant;

import com.test.fastFood.dto.address.AddressDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RestaurantDto {
    private String name;
    private AddressDto address;
}
