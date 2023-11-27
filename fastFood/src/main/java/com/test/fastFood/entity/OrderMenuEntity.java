package com.test.fastFood.entity;

import com.test.fastFood.dto.menuDTO.MenuDto;
import lombok.*;

import javax.persistence.*;

@Entity
//@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@ToString(exclude = {"order", "menu"})
@Table(name = "Orders_Menu_Items")
public class OrderMenuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;


    public MenuDto getMenu() {
        return MenuDto.builder()
                .name(menu.getName())
                .price(menu.getPrice())
                .build();
    }

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private MenuEntity menu;

    public void setOrder(OrderEntity order) {
        this.order = order;
        order.getOrderMenuEntities().add(this);
    }

    public void setMenu(MenuEntity menu) {
        this.menu = menu;
        menu.getOrderMenuEntities().add(this);
    }
}