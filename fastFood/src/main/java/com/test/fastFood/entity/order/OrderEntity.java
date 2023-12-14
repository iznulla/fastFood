package com.test.fastFood.entity.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.fastFood.entity.user.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
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

    private Integer quantity;
    private Integer totalPrice;

    @Builder.Default
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<OrderMenuEntity> orderMenuEntities = new ArrayList<>();

    @OneToOne(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private OrderInformation information;
}
