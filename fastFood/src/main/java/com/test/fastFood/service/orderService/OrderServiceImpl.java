package com.test.fastFood.service.orderService;

import com.test.fastFood.dto.orderDTO.OrderBuilder;
import com.test.fastFood.dto.orderDTO.OrderDto;
import com.test.fastFood.dto.orderDTO.OrderStatusDto;
import com.test.fastFood.entity.*;
import com.test.fastFood.repository.OrderMenuRepository;
import com.test.fastFood.repository.OrderRepository;
import com.test.fastFood.service.menuService.MenuService;
import com.test.fastFood.service.userService.UserServiceImpl;
import com.test.fastFood.utils.OrderUtils;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Data
@Builder
@Service
public class OrderServiceImpl implements OrderService{

    @Autowired private OrderRepository orderRepository;
    @Autowired private UserServiceImpl userService;
    @Autowired private MenuService menuService;
    @Autowired private OrderMenuRepository orderMenuRepository;

    @Override
    public OrderEntity createOrder(OrderDto orderDto) {
        Integer totalSum = 0;
        Integer totalQuantity = 0;

        UserEntity user = userService.getUserById(6L).orElseThrow();
        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setUser(user);
//        orderEntity.setOrderAt(Instant.now());

        List<OrderMenuEntity> orderMenuEntities = new ArrayList<>();

        for (OrderBuilder orderBuilder : orderDto.getOrderMenu()) {
            OrderMenuEntity orderMenuEntity = new OrderMenuEntity();
            orderMenuEntity.setOrder(orderEntity);
            MenuEntity menu = menuService.findById(orderBuilder.getMenuId()).orElseThrow();
            orderMenuEntity.setMenu(menu);
            orderMenuEntity.setQuantity(orderBuilder.getQuantity());
            totalSum += menu.getPrice();
            totalQuantity += orderBuilder.getQuantity();
            orderMenuEntities.add(orderMenuEntity);
        }
        OrderInformation orderInformation = OrderUtils.getOrderInformation(
                orderEntity,
                totalQuantity,
                3.0
        );
        orderEntity.setTotalPrice(totalSum);
        orderEntity.setQuantity(totalQuantity);
        orderEntity.setOrderMenuEntities(orderMenuEntities);
        orderEntity.setInformation(orderInformation);

        orderRepository.save(orderEntity);
        return orderEntity;
    }

    @Override
    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<OrderEntity> getOrderById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<OrderEntity> getOrdersByUser(Long id) {
        UserEntity user = userService.getUserById(id).orElseThrow();
        return orderRepository.findByUser(user);
    }

    @Override
    public OrderEntity updateOrder(Long id, OrderStatus orderStatus) {
        OrderEntity order = orderRepository.findById(id).orElseThrow();
        orderRepository.save(order);
        return order;
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.delete(orderRepository.findById(id).orElseThrow());
    }
}
