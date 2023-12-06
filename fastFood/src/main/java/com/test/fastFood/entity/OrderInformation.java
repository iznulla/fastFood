package com.test.fastFood.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.fastFood.enums.OrderStatus;
import com.test.fastFood.utils.DeliveryInfo;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private OrderEntity order;
    private Instant orderAt;
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;
    @OneToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    private Instant deliveryTime;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private RestaurantEntity restaurant;

    @Transient
    private DeliveryInfo deliveryInfo;
    public void setRestaurant(RestaurantEntity restaurant) {
        this.restaurant = restaurant;
        restaurant.getOrders().add(this);
    }

    public String getAddressToString() {
        return String.format(
                "%s, %s, %s",
                address.getStreet(),
                address.getCity().getName(),
                address.getCountry().getName()
        );
    }
}
