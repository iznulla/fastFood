package com.test.fastFood.dto.address;

import com.test.fastFood.entity.UserEntity;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    private String street;
    private String country;
    private String city;
    private Double longitude;
    private Double latitude;
}
