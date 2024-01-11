package com.test.fastFood.service.menu;

import com.test.fastFood.dto.menu.MenuDto;
import com.test.fastFood.entity.restaurant.MenuEntity;
import com.test.fastFood.entity.restaurant.RestaurantEntity;
import com.test.fastFood.exception.ResourceNotFoundException;
import com.test.fastFood.repository.MenuRepository;
import com.test.fastFood.repository.RestaurantRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MenuServiceTest {
    @Mock
    private MenuRepository menuRepository;
    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private MenuServiceImpl menuService;

    private MenuDto menuDto;
    private MenuEntity menu;
    private RestaurantEntity restaurant;

    @BeforeEach
    public void setUp() {
         menu = MenuEntity.builder()
                .id(1L)
                .name("Test")
                .cookingTime(5)
                .createAt(Instant.now())
                .price(10)
                .build();

         menuDto = MenuDto.builder()
                .name("Test")
                .price(10)
                .build();
         restaurant = RestaurantEntity.builder()
                .id(1L)
                .name("Test")
                .build();
        restaurant.getMenus().add(menu);
//
//        RestaurantDto restaurantDto = ConvertDtoUtils.convertRestaurantToDto(restaurant);
    }


    @Test
    public void MenuService_createMenu_ReturnNon() {
        when(restaurantRepository.findById(menuDto.getRestaurantId())).thenReturn(Optional.of(restaurant));

        menuService.create(menuDto);

        verify(restaurantRepository, times(1)).findById(menuDto.getRestaurantId());
        verify(menuRepository, times(1)).save(any(MenuEntity.class));
    }

    @Test
    public void MenuService_createMenu_ReturnThrow() {
        assertThrows(ResourceNotFoundException.class, () -> menuService.create(menuDto));
    }

    @Test
    public void MenuService_updateMenu_ReturnMenuEntity() {
        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu));
        assertAll(
                () -> Assertions.assertThat(menuService.update(1L, menuDto)).isNotNull(),
                () -> Assertions.assertThat(menuService.update(1L, menuDto).orElseThrow().getName()).isEqualTo(
                        menuDto.getName()
                ),
                () -> Assertions.assertThat(menuService.update(1L, menuDto).orElseThrow().getPrice()).isEqualTo(
                        menu.getPrice()
                )
                );

    }

    @Test
    public void MenuService_getMenuById_ReturnedMenuEntity() {
        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu));
        Assertions.assertThat(menuService.findById(1L)).isNotNull();
        Assertions.assertThat(menuService.findById(1L).orElseThrow().getName()).isEqualTo(
                menu.getName()
        );
        Assertions.assertThat(menuService.findById(1L).orElseThrow().getPrice()).isEqualTo(
                menu.getPrice()
        );
    }

    @Test
    public void MenuService_deleteMenu_ReturnVoid() {
        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu));
        menuService.delete(1L);
    }
}
