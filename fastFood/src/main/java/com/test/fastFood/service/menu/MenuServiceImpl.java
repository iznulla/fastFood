package com.test.fastFood.service.menu;

import com.test.fastFood.dto.menu.MenuDto;
import com.test.fastFood.entity.restaurant.MenuEntity;
import com.test.fastFood.entity.restaurant.RestaurantEntity;
import com.test.fastFood.exception.ResourceNotFoundException;
import com.test.fastFood.repository.MenuRepository;
import com.test.fastFood.repository.RestaurantRepository;
import com.test.fastFood.utils.ConvertDtoUtils;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Data
@Builder
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService{
    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public Optional<MenuDto> create(MenuDto menuDto) {
        RestaurantEntity restaurant = restaurantRepository.findById(menuDto.getRestaurantId()).orElseThrow(() ->
                new ResourceNotFoundException(
                        String.format("Restaurant with id %d not found", menuDto.getRestaurantId())
                ));
        try {
            MenuEntity menu = MenuEntity.builder()
                    .name(menuDto.getName())
                    .price(menuDto.getPrice())
                    .createAt(Instant.now())
                    .cookingTime(menuDto.getCookingTime())
                    .build();
            menu.setRestaurant(restaurant);
            log.debug("Created menu by name {}", menuDto.getName());
            menuRepository.save(menu);
            return Optional.of(ConvertDtoUtils.MenuEntityToDto(menu));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ResourceNotFoundException("Invalid input data");
        }
    }

    @Override
    public List<MenuDto> findAllMenu() {
        return menuRepository.findAll().stream().map(ConvertDtoUtils::MenuEntityToDto).collect(Collectors.toList());
    }

    @Override
    public Optional<MenuEntity> findById(Long id) {
        return Optional.of(menuRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(
                        String.format("Menu with id %d not found", id)
                )
        ));
    }

    @Override
    public Optional<MenuDto> findByName(String name) {
        return Optional.ofNullable(ConvertDtoUtils.MenuEntityToDto(menuRepository.findByName(name).orElseThrow()));
    }

    @Override
    public Optional<MenuDto> update(Long id, MenuDto menuDto) {
        MenuEntity menu  = menuRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                String.format("Menu with id %d not found", id)
        ));
        menu.setName(menuDto.getName());
        menu.setPrice(menuDto.getPrice());
        menuRepository.save(menu);
        log.warn("Updated menu by id {}", id);
        return Optional.of(ConvertDtoUtils.MenuEntityToDto(menu));
    }


    @Override
    public void delete(Long id) {
        MenuEntity menu = menuRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                String.format("Menu with id %d not found", id)
        ));
        log.warn("deleted menu by id {}", id);
        menuRepository.delete(menu);
    }
}
