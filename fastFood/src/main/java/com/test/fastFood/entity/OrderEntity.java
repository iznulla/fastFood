package com.test.fastFood.entity;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Data
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
    private UserEntity user;

    @Builder.Default
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<OrderMenuEntity> orderMenuEntities = new ArrayList<>();

    private Integer quantity;
    private Instant orderAt;
    private Integer totalPrice;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public UserEntity getUser() {
//        return user;
//    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<OrderMenuEntity> getOrderMenuEntities() {
        return orderMenuEntities;
    }

    public void setOrderMenuEntities(List<OrderMenuEntity> orderMenuEntities) {
        this.orderMenuEntities = orderMenuEntities;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Instant getOrderAt() {
        return orderAt;
    }

    public void setOrderAt(Instant orderAt) {
        this.orderAt = orderAt;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

}
