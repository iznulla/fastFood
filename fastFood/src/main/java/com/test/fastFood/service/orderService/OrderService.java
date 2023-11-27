package com.test.fastFood.service.orderService;

import com.test.fastFood.dto.orderDTO.OrderDto;
import com.test.fastFood.dto.orderDTO.OrderStatusDto;
import com.test.fastFood.entity.MenuEntity;
import com.test.fastFood.entity.OrderEntity;
import com.test.fastFood.entity.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface OrderService {
    void createOrder(OrderDto orderDto);
    List<OrderEntity> getAllOrders();
    Optional<OrderEntity> findOrderById(Long id);
    List<OrderEntity>  findOrdersByUser(Long id);
    void updateOrder(Long id, OrderStatusDto orderStatus);
    void deleteOrder(Long id);
}
