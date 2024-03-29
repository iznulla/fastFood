package com.test.fastFood.dto.order;

import com.test.fastFood.entity.order.OrderEntity;
import com.test.fastFood.entity.order.OrderMenuEntity;
import com.test.fastFood.enums.OrderStatus;
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
    private Instant createdAt;
    private OrderStatus orderStatus;
    private Instant delivery;
    private String restaurant;
}
