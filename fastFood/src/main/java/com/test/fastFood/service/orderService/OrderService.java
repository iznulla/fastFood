package com.test.fastFood.service.orderService;

import com.test.fastFood.dto.orderDTO.OrderCreateDto;
import com.test.fastFood.entity.OrderEntity;
import com.test.fastFood.entity.OrderStatus;

import java.util.List;
import java.util.Optional;


public interface OrderService {
    Optional<OrderEntity> createOrder(OrderCreateDto orderCreateDto);
    List<OrderEntity> getAllOrders();
    Optional<OrderEntity> getOrderById(Long id);
    List<OrderEntity> getOrdersByUser(Long id);
    Optional<OrderEntity> updateOrder(Long id, OrderStatus orderStatus);
    void deleteOrder(Long id);
}
