package com.test.fastFood.service.restaurant;

import com.test.fastFood.dto.filial.RestaurantFilialDto;
import com.test.fastFood.dto.menu.MenuDto;
import com.test.fastFood.dto.restaurant.RestaurantDto;
import com.test.fastFood.entity.restaurant.RestaurantEntity;
import com.test.fastFood.exception.ResourceNotFoundException;
import com.test.fastFood.repository.RestaurantRepository;
import com.test.fastFood.utils.ConvertDtoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements  RestaurantService {
    private final RestaurantRepository restaurantRepository;

    @Override
    public Optional<RestaurantDto> createRestaurant(RestaurantDto restaurantDto) {
        try {
            RestaurantEntity restaurant = RestaurantEntity.builder()
                    .name(restaurantDto.getName())
                    .build();
            restaurantRepository.save(restaurant);
            return Optional.of(ConvertDtoUtils.convertRestaurantToDto(restaurant));
        } catch (Exception e) {
            throw new ResourceNotFoundException(
                    "Invalid input Data"
            );
        }
    }

    @Override
    public Optional<RestaurantDto> updateRestaurant(Long id, RestaurantDto restaurantDto) {

        RestaurantEntity restaurant = restaurantRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Not found restaurant with id: " + id));

        try {
            restaurant.setName(restaurantDto.getName());
            restaurantRepository.save(restaurant);
            return Optional.of(ConvertDtoUtils.convertRestaurantToDto(restaurant));
        } catch (Exception e) {
            throw new ResourceNotFoundException(
                    "Invalid input Data"
            );
        }
    }

    @Override
    public Optional<RestaurantDto> getRestaurantById(Long id) {
        RestaurantEntity restaurant = restaurantRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not found restaurant with id: " + id
        ));
        return Optional.of(ConvertDtoUtils.convertRestaurantToDto(restaurant));
    }

    @Override
    public List<RestaurantDto> getAllRestaurant() {
        return restaurantRepository.findAll().stream().map(ConvertDtoUtils::convertRestaurantToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteRestaurantById(Long id) {
        RestaurantEntity restaurant = restaurantRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not found restaurant with id: " + id)
        );
        restaurantRepository.deleteById(restaurant.getId());
    }

    @Override
    public List<MenuDto> getMenusByRestaurantId(Long id) {
        RestaurantEntity restaurant = restaurantRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not found restaurant with id: " + id)
        );
        return restaurant.getMenus().stream().map(ConvertDtoUtils::MenuEntityToDto).collect(Collectors.toList());
    }

    @Override
    public List<RestaurantFilialDto> getFilialsByRestaurant(Long id) {
        RestaurantEntity restaurant = restaurantRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not found restaurant with id: " + id)
        );
        return restaurant.getRestaurantFilial().stream().map(ConvertDtoUtils::convertRestaurantFilialToDto)
                .collect(Collectors.toList());
    }
}
