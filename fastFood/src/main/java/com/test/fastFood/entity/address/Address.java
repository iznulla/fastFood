package com.test.fastFood.entity.address;

import com.test.fastFood.entity.order.OrderInformation;
import com.test.fastFood.entity.restaurant.RestaurantFilial;
import com.test.fastFood.entity.user.UserProfile;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private CountryEntity country;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private CityEntity city;


    @OneToOne(mappedBy = "address")
    private UserProfile userProfile;

    @OneToOne(mappedBy = "address")
    private OrderInformation orderInformation;

    @OneToOne(mappedBy = "address")
    private RestaurantFilial restaurantFilial;

    private Double longitude;
    private Double latitude;
}
