package com.test.fastFood.dto.orderDTO;

import com.test.fastFood.entity.MenuEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long userId;
    private Integer quantity;
    private List<MenuEntity> menuEntityList;
}
