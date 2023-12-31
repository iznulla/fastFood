package com.test.fastFood.entity.order;

import com.test.fastFood.dto.order.OrderMenuDto;
import com.test.fastFood.entity.restaurant.MenuEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders_menu_items")
public class OrderMenuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;
    @ManyToOne
    @JoinColumn(name = "menu_id")
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