package com.test.fastFood.dto.order;

import com.test.fastFood.enums.OrderStatus;
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
public class OrderStatusDto {
    private OrderStatus orderStatus;
}
