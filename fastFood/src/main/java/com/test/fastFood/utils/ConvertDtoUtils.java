package com.test.fastFood.utils;

import com.test.fastFood.dto.menu.MenuDto;
import com.test.fastFood.dto.order.OrderDto;
import com.test.fastFood.dto.user.UserDto;
import com.test.fastFood.entity.MenuEntity;
import com.test.fastFood.entity.OrderEntity;
import com.test.fastFood.enums.OrderStatus;
import com.test.fastFood.entity.UserEntity;

public class ConvertDtoUtils {

    public static MenuDto MenuEntityToDto(MenuEntity menu) {
        return MenuDto.builder()
                .name(menu.getName())
                .price(menu.getPrice())
                .cookingTime(menu.getCookingTime())
                .build();
    }

    public static UserDto convertUserToDto(UserEntity user) {
        return UserDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .name(user.getUserProfile().getName())
                .surname(user.getUserProfile().getSurname())
                .address(user.getUserProfile().getAddress())
                .ordersCount(user.getOrders().size())
                .build();
    }

    public static OrderDto convertOrderToDto(OrderEntity order) {
        return OrderDto.builder()
                .username(order.getUser().getUsername())
                .orders(order.getOrderMenuEntities())
                .priceToPay(order.getTotalPrice())
                .totalQuantity(order.getQuantity())
                .orderStatus(OrderStatus.valueOf(order.getInformation().getOrderStatus().name()))
                .address(order.getInformation().getAddress())
                .delivery(order.getInformation().getDelivery())
                .build();
    }
}
