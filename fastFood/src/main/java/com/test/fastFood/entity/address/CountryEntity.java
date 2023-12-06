package com.test.fastFood.entity.address;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "country")
public class CountryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<CityEntity> cities;

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Address> addresses;
}