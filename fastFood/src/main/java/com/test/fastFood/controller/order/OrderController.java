package com.test.fastFood.controller.order;


import com.test.fastFood.dto.order.OrderCreateDto;
import com.test.fastFood.dto.order.OrderDto;
import com.test.fastFood.dto.order.OrderStatusDto;
import com.test.fastFood.service.order.OrderService;
import com.test.fastFood.utils.ConvertDtoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderCreateDto orderCreateDto) {
        return new ResponseEntity<>(ConvertDtoUtils.convertOrderToDto(orderService.createOrder(orderCreateDto).orElseThrow()),
                HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'WAITER')")
    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return new ResponseEntity<>(orderService.getAllOrders()
                .stream().map(ConvertDtoUtils::convertOrderToDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'WAITER')")
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrdersById(@PathVariable Long id) {
        return new ResponseEntity<>(ConvertDtoUtils.convertOrderToDto(orderService.getOrderById(id).orElseThrow()), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'WAITER')")
    @PatchMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long id, @RequestBody OrderStatusDto orderStatus) {
        return new ResponseEntity<>(ConvertDtoUtils.convertOrderToDto(orderService.updateOrder(id, orderStatus.getOrderStatus()).orElseThrow()),
                HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'WAITER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
