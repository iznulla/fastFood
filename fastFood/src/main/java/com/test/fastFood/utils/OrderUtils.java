package com.test.fastFood.utils;

import com.test.fastFood.dto.order.OrderBuilder;
import com.test.fastFood.entity.address.Address;
import com.test.fastFood.entity.order.OrderEntity;
import com.test.fastFood.entity.order.OrderInformation;
import com.test.fastFood.entity.order.OrderMenuEntity;
import com.test.fastFood.entity.restaurant.MenuEntity;
import com.test.fastFood.entity.restaurant.RestaurantEntity;
import com.test.fastFood.entity.restaurant.RestaurantFilial;
import com.test.fastFood.enums.OrderStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.awt.geom.Point2D;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static java.lang.Math.round;

@Getter
@RequiredArgsConstructor
public class OrderUtils {
    int totalSum;
    int totalQuantity;
    int cookTime;
    static int capacity = 5;

    public void addingMenuInOrders(OrderMenuEntity orderMenuEntity, OrderBuilder orderBuilder, MenuEntity menu, List<OrderMenuEntity> orderMenuEntities) {
        orderMenuEntity.setMenu(menu);
        orderMenuEntity.setQuantity(orderBuilder.getQuantity());
        totalSum += menu.getPrice() * orderBuilder.getQuantity();
        totalQuantity += orderBuilder.getQuantity();
        cookTime += menu.getCookingTime() * orderBuilder.getQuantity();
        orderMenuEntities.add(orderMenuEntity);
    }

    public static Instant getOrderInformation(Integer time, Integer quantity, Double distance) {
        int timeCalculate = timeCalculate(time, quantity, capacity, distance);
        return Instant.now().plus(Duration.of(timeCalculate, ChronoUnit.SECONDS));
    }

    private static int timeCalculate(Integer time, Integer quantity, int capacity, Double distance) {
        int fullPeriods = quantity / capacity;
        // Рассчитываем оставшееся количество блюд после полных периодов
        int remainingDishes = quantity % capacity;
        // Рассчитываем время для полных периодов
        double fullPeriodTime = (double) fullPeriods * capacity * time;
        // Рассчитываем время для оставшихся блюд, если они есть
        double remainingTime = (double) remainingDishes * time / capacity;
        // Общее время приготовления
        double cookingTime = fullPeriodTime + remainingTime;
        // Рассчитываем время доставки
        double deliveryTime = (distance * 200) * (3 * 60);
        // Общее время, включая приготовление и доставку
        double totalTime = cookingTime + deliveryTime;
        return (int) Math.round(totalTime);
    }

    public void createAndFillOrderInformation(OrderEntity orderEntity, OrderInformation orderInformation, RestaurantEntity restaurant) {
        DeliveryInfo deliveryInfo = getClosestAddress(restaurant, orderInformation);
        orderInformation.setOrder(orderEntity);
        orderInformation.setOrderAt(Instant.now());
        orderInformation.setDeliveryTime(getOrderInformation(cookTime, totalQuantity, deliveryInfo.getDistance()));
        orderInformation.setDeliveryInfo(deliveryInfo);
        orderInformation.setOrderStatus(OrderStatus.NEW);
        orderInformation.setRestaurantFilial(deliveryInfo.getRestaurantFilial());
    }

    public static DeliveryInfo getClosestAddress(RestaurantEntity restaurant, OrderInformation orderInformation) {
        List<RestaurantFilial> restaurantAddress = restaurant.getRestaurantFilial();
        Address orderAddress = orderInformation.getAddress();
        RestaurantFilial resFilial = restaurantAddress.get(0);
        double distance = Point2D.distance(orderAddress.getLongitude(), orderAddress.getLatitude(),
                resFilial.getAddress().getLongitude(), resFilial.getAddress().getLatitude());
        double minDistance = distance;
        for (RestaurantFilial r : restaurantAddress) {
            distance = Point2D.distance(orderAddress.getLongitude(),
                    orderAddress.getLatitude(), r.getAddress().getLongitude(), r.getAddress().getLatitude());
            if (distance < minDistance) {
                minDistance = distance;
                resFilial = r;
            }
        }
        return DeliveryInfo.builder()
                .restaurantFilial(resFilial)
                .distance(minDistance)
                .build();
    }
}
