package com.test.fastFood.dto.order;

import com.test.fastFood.dto.address.AddressDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateDto {
    private AddressDto address;
    private List<OrderBuilder> orderMenu;
}
