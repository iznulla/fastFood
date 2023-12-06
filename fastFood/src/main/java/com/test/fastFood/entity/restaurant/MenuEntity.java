package com.test.fastFood.entity.restaurant;

import com.test.fastFood.entity.order.OrderMenuEntity;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "menu")
public class MenuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private Integer price;
    private Instant createAt;
    private Integer cookingTime;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private RestaurantEntity restaurant;

    @Builder.Default
    @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<OrderMenuEntity> orderMenuEntities = new ArrayList<>();

    public void setRestaurant(RestaurantEntity restaurant) {
        this.restaurant = restaurant;
        restaurant.getMenus().add(this);
    }
}
