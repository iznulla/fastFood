package com.test.fastFood.service.order;

import com.test.fastFood.dto.order.OrderCreateDto;
import com.test.fastFood.dto.order.OrderDto;
import com.test.fastFood.entity.order.OrderEntity;
import com.test.fastFood.enums.OrderStatus;

import java.util.List;
import java.util.Optional;


public interface OrderService {
    Optional<OrderEntity> createOrder(OrderCreateDto orderCreateDto);
    List<OrderEntity> getAllOrders();
    Optional<OrderEntity> getOrderById(Long id);
    List<OrderEntity> getOrdersByUser(Long id);
    Optional<OrderDto> updateOrder(Long id, OrderStatus orderStatus);
    void deleteOrder(Long id);
}
