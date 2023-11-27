package com.test.fastFood.service.orderService;

import com.test.fastFood.dto.orderDTO.OrderDto;
import com.test.fastFood.entity.MenuEntity;
import com.test.fastFood.entity.OrderEntity;
import com.test.fastFood.entity.OrderMenuEntity;
import com.test.fastFood.entity.UserEntity;
import com.test.fastFood.repository.OrderMenuRepository;
import com.test.fastFood.repository.OrderRepository;
import com.test.fastFood.service.menuService.MenuService;
import com.test.fastFood.service.userService.UserServiceImpl;
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
    public void createOrder(OrderDto orderDto) {
        UserEntity user = userService.getUserById(7L).orElseThrow();
        OrderEntity orderEntity = new OrderEntity();
//        orderEntity.setUser(user);
        orderEntity.setOrderAt(Instant.now());
        orderEntity.setTotalPrice(calculateTotalPrice(orderDto.getMenuEntityList()));
        orderEntity.setQuantity(3);

        List<OrderMenuEntity> orderMenuEntities = new ArrayList<>();

        for (MenuEntity menuEntity : orderDto.getMenuEntityList()) {
            OrderMenuEntity orderMenuEntity = new OrderMenuEntity();
            orderMenuEntity.setOrder(orderEntity);
            orderMenuEntity.setMenu(menuEntity);
            orderMenuEntities.add(orderMenuEntity);
        }

        orderEntity.setOrderMenuEntities(orderMenuEntities);
        orderRepository.save(orderEntity);
    }

    private Integer calculateTotalPrice(List<MenuEntity> menuEntities) {
        // Implement your logic to calculate total price based on menu items
        // This is just a placeholder, replace it with your actual logic
        return menuEntities.stream().mapToInt(MenuEntity::getPrice).sum();
    }


    @Override
    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<OrderEntity> findOrderById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<OrderEntity> findOrdersByUser(Long id) {
        UserEntity user = userService.getUserById(id).orElseThrow();
        return orderRepository.findByUser(user);
    }
}
