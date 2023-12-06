package com.test.fastFood.utils;

import com.test.fastFood.dto.order.OrderBuilder;
import com.test.fastFood.entity.*;
import com.test.fastFood.enums.OrderStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.awt.geom.Point2D;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class OrderUtils {

    int totalSum;
    int totalQuantity;
    int cookTime;
    public void addingMenuInOrders(OrderMenuEntity orderMenuEntity, OrderBuilder orderBuilder, MenuEntity menu, List<OrderMenuEntity> orderMenuEntities) {
        orderMenuEntity.setMenu(menu);
        orderMenuEntity.setQuantity(orderBuilder.getQuantity());
        totalSum += menu.getPrice() * orderBuilder.getQuantity();
        totalQuantity += orderBuilder.getQuantity();
        cookTime += menu.getCookingTime() * orderBuilder.getQuantity();
        orderMenuEntities.add(orderMenuEntity);
    }

    public static Instant getOrderInformation(Integer time, Integer quantity, Double distance) {
        int timeCalculate = timeCalculate(time, quantity, distance);
        return Instant.now().plus(Duration.of(timeCalculate, ChronoUnit.SECONDS));
    }

    private static int timeCalculate(Integer time, Integer quantity, Double distance) {
        int cookingTime =  (time * 60) / quantity ;
        int deliveryTime = (int) (distance * 500)  * (3 * 60);
        return deliveryTime + cookingTime;
    }

    public void createAndFillOrderInformation(OrderEntity orderEntity, OrderInformation orderInformation, RestaurantEntity restaurant) {
        DeliveryInfo deliveryInfo = getClosestAddress(restaurant, orderInformation);
        orderInformation.setOrder(orderEntity);
        orderInformation.setOrderAt(Instant.now());
        orderInformation.setDeliveryTime(getOrderInformation(cookTime, totalQuantity, deliveryInfo.getDistance()));
        orderInformation.setDeliveryInfo(deliveryInfo);
        orderInformation.setOrderStatus(OrderStatus.PROCESSING);
        orderInformation.setRestaurant(restaurant);
    }

    public static DeliveryInfo getClosestAddress(RestaurantEntity restaurant, OrderInformation orderInformation) {
        List<Address> restaurantAddress = restaurant.getAddress();
        Address orderAddress = orderInformation.getAddress();
        Address resAddress = restaurantAddress.get(0);
        double distance = Point2D.distance(orderAddress.getLongitude(), orderAddress.getLatitude(),
                resAddress.getLongitude(), resAddress.getLatitude());
        double minDistance = distance;
        for (Address a : restaurantAddress) {
            distance = Point2D.distance(orderAddress.getLongitude(),
                    orderAddress.getLatitude(), a.getLongitude(), a.getLatitude());
            if (distance < minDistance) {
                minDistance = distance;
                resAddress = a;
            }
        }
        return DeliveryInfo.builder()
                .address(resAddress)
                .distance(minDistance)
                .build();
    }
}
