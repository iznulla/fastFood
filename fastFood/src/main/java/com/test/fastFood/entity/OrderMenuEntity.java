package com.test.fastFood.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.fastFood.dto.menuDTO.MenuDto;
import com.test.fastFood.dto.orderDTO.OrderMenuDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Orders_Menu_Items")
public class OrderMenuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private OrderEntity order;
    @ManyToOne
    @JoinColumn(name = "menu_id")
    @JsonIgnore
    private MenuEntity menu;
    private Integer quantity;


//    public MenuDto getMenu() {
//        return MenuDto.builder()
//                .name(menu.getName())
//                .price(menu.getPrice())
//                .build();
//    }

    public void setOrder(OrderEntity order) {
        this.order = order;
        order.getOrderMenuEntities().add(this);
    }

    public void setMenu(MenuEntity menu) {
        this.menu = menu;
        menu.getOrderMenuEntities().add(this);
    }
}