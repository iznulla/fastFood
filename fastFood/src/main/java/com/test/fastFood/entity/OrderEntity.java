package com.test.fastFood.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private UserEntity user;

    @Builder.Default
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<OrderMenuEntity> orderMenuEntities = new ArrayList<>();

    private Integer quantity;
    private Instant orderAt;
    private Integer totalPrice;
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.PROCESSING;

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }


}
