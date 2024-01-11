package com.test.fastFood.service.order;

import com.test.fastFood.dto.order.OrderBuilder;
import com.test.fastFood.dto.order.OrderCreateDto;
import com.test.fastFood.entity.address.Address;
import com.test.fastFood.entity.order.OrderEntity;
import com.test.fastFood.entity.order.OrderInformation;
import com.test.fastFood.entity.order.OrderMenuEntity;
import com.test.fastFood.entity.restaurant.MenuEntity;
import com.test.fastFood.entity.restaurant.RestaurantEntity;
import com.test.fastFood.entity.user.UserEntity;
import com.test.fastFood.enums.OrderStatus;
import com.test.fastFood.exception.ResourceNotFoundException;
import com.test.fastFood.repository.OrderRepository;
import com.test.fastFood.repository.RestaurantRepository;
import com.test.fastFood.service.address.AddressService;
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
    private final RestaurantRepository restaurantRepository;
    private final AddressService addressService;

    @Override
    public Optional<OrderEntity> createOrder(OrderCreateDto orderCreateDto) {
        OrderUtils orderUtils = new OrderUtils();
        Long userId = SecurityUtils.getCurrentUserId();
        UserEntity user = userService.getUserById(userId).orElseThrow(
                () -> new ResourceNotFoundException(
                        String.format("User with id %d not found", SecurityUtils.getCurrentUserId())
                )
        );

        Address address = addressService.createAddress(orderCreateDto.getAddress()).orElseThrow();

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUser(user);

        List<OrderMenuEntity> orderMenuEntities = new ArrayList<>();
        Long restaurantId = null;

        for (OrderBuilder orderBuilder : orderCreateDto.getOrderMenu()) {
            OrderMenuEntity orderMenuEntity = new OrderMenuEntity();
            orderMenuEntity.setOrder(orderEntity);

            MenuEntity menu = menuService.findById(orderBuilder.getMenuId()).orElseThrow(
                    () -> new ResourceNotFoundException(
                            String.format("Menu with id %d not found", orderBuilder.getMenuId())
                    )
            );
            restaurantId = menu.getRestaurant().getId();
            orderUtils.addingMenuInOrders(orderMenuEntity, orderBuilder, menu, orderMenuEntities);
        }

        Long finalRestaurantId = restaurantId;
        RestaurantEntity restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new ResourceNotFoundException(
                String.format("Restaurant with id %d not found", finalRestaurantId)
        ));

        // order information
        OrderInformation orderInformation = new OrderInformation();
        orderInformation.setAddress(address);
        orderUtils.createAndFillOrderInformation(orderEntity, orderInformation, restaurant);

        // fill order entity
        orderEntity.setTotalPrice(orderUtils.getTotalSum());
        orderEntity.setQuantity(orderUtils.getTotalQuantity());
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
                () -> new ResourceNotFoundException(
                        String.format("Order with id %d not found", id)
                )
        );
        return Optional.of(order);
    }

    @Override
    public List<OrderEntity> getOrdersByUser(Long id) {
        UserEntity user = userService.getUserById(id).orElseThrow(
                () -> new ResourceNotFoundException(
                        String.format("User with id %d not found", id)
                )
        );
        return orderRepository.findByUser(user);
    }

    @Override
    public Optional<OrderEntity> updateOrder(Long id, OrderStatus orderStatus) {
        OrderEntity order = orderRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(
                        String.format("Order with id %d not found", id)
                )
        );
        order.getInformation().setOrderStatus(orderStatus);
        orderRepository.save(order);
        log.warn("Order status is changed to: {}", orderStatus);
        return Optional.of(order);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.delete(orderRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(
                        String.format("Order with id %d not found", id)
                )
        ));
        log.warn("Order deleted by id {}", id);
    }
}
