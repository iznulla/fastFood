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
    @JoinColumn(name = "restaurantFilial_id", referencedColumnName = "id")
    private RestaurantFilial restaurantFilial;

    @Transient
    private DeliveryInfo deliveryInfo;
    public void setRestaurantFilial(RestaurantFilial restaurantFilial) {
        this.restaurantFilial = restaurantFilial;
        restaurantFilial.getOrders().add(this);
    }

    public String getRestaurantToString() {
        return String.format(
                "%s, %s, %s",
                restaurantFilial.getName(),
                restaurantFilial.getAddress().getCity().getName(),
                restaurantFilial.getAddress().getCountry().getName()
        );
    }
}
