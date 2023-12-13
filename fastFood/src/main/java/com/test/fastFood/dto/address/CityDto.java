package com.test.fastFood.dto.address;


import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CityDto {
    private String name;
    private Long countryId;
}
