package com.test.fastFood.dto.orderDTO;

import com.test.fastFood.entity.OrderMenuEntity;
import com.test.fastFood.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Data
@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private String username;
    private List<OrderMenuEntity> orders;
    private Integer priceToPay;
    private Integer totalQuantity;
    private OrderStatus orderStatus;
    private Instant delivery;
}
