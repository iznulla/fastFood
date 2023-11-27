package com.test.fastFood.service.orderService;

import com.test.fastFood.dto.orderDTO.OrderBuilder;
import com.test.fastFood.dto.orderDTO.OrderDto;
import com.test.fastFood.entity.OrderEntity;
import com.test.fastFood.entity.OrderStatus;

import java.util.List;
import java.util.Optional;


public interface OrderService {
    OrderEntity createOrder(OrderDto orderDto);
    List<OrderEntity> getAllOrders();
    Optional<OrderEntity> getOrderById(Long id);
    List<OrderEntity> getOrdersByUser(Long id);
    OrderEntity updateOrder(Long id, OrderStatus orderStatus);
    void deleteOrder(Long id);
}
