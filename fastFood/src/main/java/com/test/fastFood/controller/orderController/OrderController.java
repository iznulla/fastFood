package com.test.fastFood.controller.orderController;


import com.test.fastFood.dto.orderDTO.OrderCreateDto;
import com.test.fastFood.dto.orderDTO.OrderDto;
import com.test.fastFood.dto.orderDTO.OrderStatusDto;
import com.test.fastFood.entity.OrderEntity;
import com.test.fastFood.service.orderService.OrderService;
import com.test.fastFood.utils.ConvertDtoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderCreateDto orderCreateDto) {
        return new ResponseEntity<>(ConvertDtoUtils.convertOrderToDto(orderService.createOrder(orderCreateDto).orElseThrow()),
                HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return new ResponseEntity<>(orderService.getAllOrders()
                .stream().map(ConvertDtoUtils::convertOrderToDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrdersById(@PathVariable Long id) {
        return new ResponseEntity<>(ConvertDtoUtils.convertOrderToDto(orderService.getOrderById(id).orElseThrow()), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long id, @RequestBody OrderStatusDto orderStatus) {
        return new ResponseEntity<>(ConvertDtoUtils.convertOrderToDto(orderService.updateOrder(id, orderStatus.getOrderStatus()).orElseThrow()),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id)
    {orderService.deleteOrder(id);}
}
