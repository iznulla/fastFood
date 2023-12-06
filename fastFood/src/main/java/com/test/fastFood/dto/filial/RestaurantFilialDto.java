package com.test.fastFood.dto.filial;

import com.test.fastFood.dto.address.AddressDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RestaurantFilialDto {
    private Long restaurantId;
    private String name;
    private AddressDto address;
}
