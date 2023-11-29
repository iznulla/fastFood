package com.test.fastFood.dto.menuDTO;


import com.test.fastFood.entity.MenuEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto {
    private String name;
    private Integer price;
    private Integer cookingTime;
}
