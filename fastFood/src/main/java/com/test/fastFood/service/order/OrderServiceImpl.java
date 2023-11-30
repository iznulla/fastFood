package com.test.fastFood.service.order;

import com.test.fastFood.dto.order.OrderBuilder;
import com.test.fastFood.dto.order.OrderCreateDto;
import com.test.fastFood.entity.*;
import com.test.fastFood.enums.OrderStatus;
import com.test.fastFood.exception.NotFoundException;
import com.test.fastFood.repository.OrderRepository;
import com.test.fastFood.service.menu.MenuService;
import com.test.fastFood.service.user.UserServiceImpl;
import com.test.fastFood.utils.OrderUtils;
import com.test.fastFood.utils.SecurityUtils;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Data
@Builder
@Service
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final UserServiceImpl userService;
    private final MenuService menuService;

    @Override
    public Optional<OrderEntity> createOrder(OrderCreateDto orderCreateDto) {
        int totalSum = 0;
        Integer totalQuantity = 0;

        UserEntity user = userService.getUserById(SecurityUtils.getCurrentUserId()).orElseThrow(
                () -> new NotFoundException(
                        String.format("User with id %d not found", SecurityUtils.getCurrentUserId())
                )
        );
        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setUser(user);

        List<OrderMenuEntity> orderMenuEntities = new ArrayList<>();

        for (OrderBuilder orderBuilder : orderCreateDto.getOrderMenu()) {
            OrderMenuEntity orderMenuEntity = new OrderMenuEntity();
            orderMenuEntity.setOrder(orderEntity);
            MenuEntity menu = menuService.findById(orderBuilder.getMenuId()).orElseThrow(
                    () -> new NotFoundException(
                            String.format("Menu with id %d not found", orderBuilder.getMenuId())
                    )
            );
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
        log.warn("Order created (user id : {})", user.getUserId());
        return Optional.of(orderEntity);
    }

    @Override
    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<OrderEntity> getOrderById(Long id) {
        OrderEntity order = orderRepository.findById(id).orElseThrow(
                () -> new NotFoundException(
                        String.format("Order with id %d not found", id)
                )
        );
        return Optional.of(order);
    }

    @Override
    public List<OrderEntity> getOrdersByUser(Long id) {
        UserEntity user = userService.getUserById(id).orElseThrow(
                () -> new NotFoundException(
                        String.format("User with id %d not found", id)
                )
        );
        return orderRepository.findByUser(user);
    }

    @Override
    public Optional<OrderEntity> updateOrder(Long id, OrderStatus orderStatus) {
        OrderEntity order = orderRepository.findById(id).orElseThrow(
                () -> new NotFoundException(
                        String.format("Order with id %d not found", id)
                )
        );
        order.getInformation().setOrderStatus(orderStatus);
        orderRepository.save(order);
        log.warn("Order status is changed to: {} from {}", orderStatus, SecurityUtils.getCurrentUsername());
        return Optional.of(order);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.delete(orderRepository.findById(id).orElseThrow(
                () -> new NotFoundException(
                        String.format("Order with id %d not found", id)
                )
        ));
        log.warn("Order deleted by user {}, order id {}", SecurityUtils.getCurrentUsername(), id);
    }
}
