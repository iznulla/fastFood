package com.test.fastFood.controller.orderController;


import com.test.fastFood.dto.orderDTO.OrderDto;
import com.test.fastFood.dto.orderDTO.OrderStatusDto;
import com.test.fastFood.entity.OrderEntity;
import com.test.fastFood.entity.OrderMenuEntity;
import com.test.fastFood.entity.OrderStatus;
import com.test.fastFood.service.orderService.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired private OrderService orderService;

    @GetMapping
    public List<OrderEntity> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public List<OrderEntity> getOrdersByUser(@PathVariable Long id) {
        return orderService.findOrdersByUser(id);
    }

    @PostMapping
    public void createOrder(@RequestBody OrderDto orderDto) {
        orderService.createOrder(orderDto);
    }

    @PatchMapping("/{id}")
    public void updateOrder(@PathVariable Long id, @RequestBody OrderStatusDto orderStatus) {
        orderService.updateOrder(id, orderStatus);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id)
    {orderService.deleteOrder(id);}
}
