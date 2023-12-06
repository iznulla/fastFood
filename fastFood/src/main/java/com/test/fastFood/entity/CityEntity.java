package com.test.fastFood.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "city")
public class CityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private CountryEntity country;


    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Address> addresses;
}
