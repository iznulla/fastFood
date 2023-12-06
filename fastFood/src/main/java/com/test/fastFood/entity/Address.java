package com.test.fastFood.entity;

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

    @ManyToOne
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private RestaurantEntity restaurant;

    private Double longitude;
    private Double latitude;

    public void setRestaurant(RestaurantEntity restaurant) {
        this.restaurant = restaurant;
        restaurant.getAddress().add(this);
    }
}
