package com.test.fastFood.entity.restaurant;

import com.test.fastFood.entity.address.Address;
import com.test.fastFood.entity.order.OrderInformation;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "restaurant_filial")
public class RestaurantFilial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private RestaurantEntity restaurant;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @Builder.Default
    @OneToMany(mappedBy = "restaurantFilial", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<OrderInformation> orders = new ArrayList<>();

    public void setRestaurant(RestaurantEntity restaurant) {
        this.restaurant = restaurant;
        restaurant.getRestaurantFilial().add(this);
    }
}
