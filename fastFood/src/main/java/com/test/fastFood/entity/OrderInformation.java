package com.test.fastFood.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String address;
    private Instant delivery;
}
