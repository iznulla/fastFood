package com.test.fastFood.service.restaurant;

import com.test.fastFood.dto.menu.MenuDto;
import com.test.fastFood.dto.restaurant.RestaurantDto;
import com.test.fastFood.entity.Address;
import com.test.fastFood.entity.RestaurantEntity;
import com.test.fastFood.repository.RestaurantRepository;
import com.test.fastFood.service.address.AddressService;
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
    private final AddressService addressService;

    @Override
    public Optional<RestaurantDto> createRestaurant(RestaurantDto restaurantDto) {
        Address address = addressService.createAddress(restaurantDto.getAddress()).orElseThrow();

        RestaurantEntity restaurant = RestaurantEntity.builder()
                .name(restaurantDto.getName())
                .build();
        address.setRestaurant(restaurant);
        restaurantRepository.save(restaurant);
        return Optional.of(ConvertDtoUtils.convertRestaurantToDto(restaurant));
    }

    @Override
    public Optional<RestaurantDto> updateRestaurant(Long id, RestaurantDto restaurantDto) {
        Address address = addressService.createAddress(restaurantDto.getAddress()).orElseThrow();

        RestaurantEntity restaurant = restaurantRepository.findById(id).orElseThrow();

        restaurant.setName(restaurantDto.getName());
        address.setRestaurant(restaurant);
        restaurantRepository.save(restaurant);
        return Optional.of(ConvertDtoUtils.convertRestaurantToDto(restaurant));
    }

    @Override
    public Optional<RestaurantDto> getRestaurantById(Long id) {
        RestaurantEntity restaurant = restaurantRepository.findById(id).orElseThrow();
        return Optional.of(ConvertDtoUtils.convertRestaurantToDto(restaurant));
    }

    @Override
    public List<RestaurantDto> getAllRestaurant() {
        return restaurantRepository.findAll().stream().map(ConvertDtoUtils::convertRestaurantToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteRestaurantById(Long id) {
        restaurantRepository.delete(restaurantRepository.findById(id).orElseThrow());
    }

    @Override
    public List<MenuDto> getMenusByRestaurantId(Long id) {
        RestaurantEntity restaurant = restaurantRepository.findById(id).orElseThrow();
        return restaurant.getMenus().stream().map(ConvertDtoUtils::MenuEntityToDto).collect(Collectors.toList());
    }
}
