package com.test.fastFood.service.orderService;

import com.test.fastFood.dto.orderDTO.OrderBuilder;
import com.test.fastFood.dto.orderDTO.OrderCreateDto;
import com.test.fastFood.entity.*;
import com.test.fastFood.repository.OrderRepository;
import com.test.fastFood.service.menuService.MenuService;
import com.test.fastFood.service.userService.UserServiceImpl;
import com.test.fastFood.utils.OrderUtils;
import com.test.fastFood.utils.SecurityUtils;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    @Override
    public Optional<OrderEntity> createOrder(OrderCreateDto orderCreateDto) {
        Integer totalSum = 0;
        Integer totalQuantity = 0;

        UserEntity user = userService.getUserById(SecurityUtils.getCurrentUserId()).orElseThrow();
        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setUser(user);

        List<OrderMenuEntity> orderMenuEntities = new ArrayList<>();

        for (OrderBuilder orderBuilder : orderCreateDto.getOrderMenu()) {
            OrderMenuEntity orderMenuEntity = new OrderMenuEntity();
            orderMenuEntity.setOrder(orderEntity);
            MenuEntity menu = menuService.findById(orderBuilder.getMenuId()).orElseThrow();
            orderMenuEntity.setMenu(menu);
            orderMenuEntity.setQuantity(orderBuilder.getQuantity());
            totalSum += menu.getPrice() * orderBuilder.getQuantity();
            totalQuantity += orderBuilder.getQuantity();
            orderMenuEntities.add(orderMenuEntity);
        }
        OrderInformation orderInformation = OrderUtils.getOrderInformation(
                orderEntity,
                totalQuantity,
                3.0
        );
        orderInformation.setAddress(orderCreateDto.getAddress());
        orderEntity.setTotalPrice(totalSum);
        orderEntity.setQuantity(totalQuantity);
        orderEntity.setOrderMenuEntities(orderMenuEntities);
        orderEntity.setInformation(orderInformation);

        orderRepository.save(orderEntity);
        return Optional.of(orderEntity);
    }

    @Override
    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<OrderEntity> getOrderById(Long id) {
        OrderEntity order = orderRepository.findById(id).orElseThrow();
        return Optional.of(order);
    }

    @Override
    public List<OrderEntity> getOrdersByUser(Long id) {
        UserEntity user = userService.getUserById(id).orElseThrow();
        return orderRepository.findByUser(user);
    }

    @Override
    public Optional<OrderEntity> updateOrder(Long id, OrderStatus orderStatus) {
        OrderEntity order = orderRepository.findById(id).orElseThrow();
        order.getInformation().setOrderStatus(orderStatus);
        orderRepository.save(order);
        return Optional.of(order);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.delete(orderRepository.findById(id).orElseThrow());
    }
}
