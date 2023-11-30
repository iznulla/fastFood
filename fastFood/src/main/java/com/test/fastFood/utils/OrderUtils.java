package com.test.fastFood.utils;

import com.test.fastFood.entity.OrderEntity;
import com.test.fastFood.entity.OrderInformation;
import com.test.fastFood.enums.OrderStatus;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class OrderUtils {
    public static OrderInformation getOrderInformation(OrderEntity order, Integer quantity, Double distance) {
        return OrderInformation.builder()
                .order(order)
                .orderAt(Instant.now())
                .orderStatus(OrderStatus.PROCESSING)
                .delivery(Instant.now().plus(Duration.of(timeCalculate(quantity, distance), ChronoUnit.MINUTES)))
                .build();
    }

    private static int timeCalculate(Integer quantity, Double distance) {
        double cookingTime = quantity * 1.25;
        double deliveryTime = distance * 3;
        return (int) (deliveryTime + cookingTime);
    }
}
