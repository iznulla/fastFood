package com.test.fastFood.entity;

import com.test.fastFood.dto.order.OrderMenuDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Orders_Menu_Items")
public class OrderMenuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
//    @JsonIgnore
    private OrderEntity order;
    @ManyToOne
    @JoinColumn(name = "menu_id")
//    @JsonIgnore
    private MenuEntity menu;
    private Integer quantity;


    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public OrderMenuDto getMenu() {
        return OrderMenuDto.builder()
                .name(menu.getName())
                .price(menu.getPrice())
                .quantity(quantity)
                .build();
    }


    public void setOrder(OrderEntity order) {
        this.order = order;
        order.getOrderMenuEntities().add(this);
    }

    public void setMenu(MenuEntity menu) {
        this.menu = menu;
        menu.getOrderMenuEntities().add(this);
    }
}