package com.test.fastFood.dto.orderDTO;

import com.test.fastFood.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderBuilder {
    private Long menuId;
    private Integer quantity;
}
