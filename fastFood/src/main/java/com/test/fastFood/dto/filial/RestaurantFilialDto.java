package com.test.fastFood.dto.filial;

import com.test.fastFood.dto.address.AddressDto;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantFilialDto {
    private Long restaurantId;
    private String name;
    private AddressDto address;
}
