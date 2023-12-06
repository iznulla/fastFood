package com.test.fastFood.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.fastFood.entity.Address;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Data
@RequiredArgsConstructor
@Builder
public class DeliveryInfo {
    private final Address address;
    private final Double distance;
}
