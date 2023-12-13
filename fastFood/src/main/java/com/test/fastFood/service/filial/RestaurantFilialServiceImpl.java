package com.test.fastFood.service.filial;

import com.test.fastFood.dto.filial.RestaurantFilialDto;
import com.test.fastFood.entity.address.Address;
import com.test.fastFood.entity.restaurant.RestaurantEntity;
import com.test.fastFood.entity.restaurant.RestaurantFilial;
import com.test.fastFood.exception.NotFoundException;
import com.test.fastFood.repository.RestaurantFilialRepository;
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
public class RestaurantFilialServiceImpl implements RestaurantFilialService {
    private final RestaurantFilialRepository restaurantFilialRepository;
    private final RestaurantRepository restaurantRepository;
    private final AddressService addressService;
    @Override
    public Optional<RestaurantFilialDto> createRestaurantFilial(RestaurantFilialDto restaurantfilialDto) {
        RestaurantEntity restaurant = restaurantRepository.findById(restaurantfilialDto.getRestaurantId()).orElseThrow(() ->
                new RuntimeException(String.format(
                        "Restaurant by id: %d not found", restaurantfilialDto.getRestaurantId())));
        Address address = addressService.createAddress(restaurantfilialDto.getAddress()).orElseThrow();
        RestaurantFilial restaurantFilial = RestaurantFilial.builder()
                .name(restaurantfilialDto.getName())
                .address(address)
                .build();
        restaurantFilial.setRestaurant(restaurant);
        restaurantFilialRepository.save(restaurantFilial);
        return Optional.of(ConvertDtoUtils.convertRestaurantFilialToDto(restaurantFilial));
    }

    @Override
    public Optional<RestaurantFilialDto> updateRestaurantFilial(Long id, RestaurantFilialDto restaurantfilialDto) {
        RestaurantFilial restaurantFilial = restaurantFilialRepository.findById(id).orElseThrow(() ->
                new RuntimeException(String.format(
                        "RestaurantFilial by id: %d not found", id
                )));
        Address address = addressService.updateAddress(restaurantFilial.getAddress().getId(),
                restaurantfilialDto.getAddress()).orElseThrow(() ->
                new NotFoundException("Not found address"));
        restaurantFilial.setName(restaurantfilialDto.getName());
        restaurantFilial.setAddress(address);
        return Optional.of(ConvertDtoUtils.convertRestaurantFilialToDto(restaurantFilial));
    }

    @Override
    public Optional<RestaurantFilialDto> getRestaurantFilialById(Long id) {
        RestaurantFilial restaurantFilial = restaurantFilialRepository.findById(id).orElseThrow(() ->
                new RuntimeException(String.format(
                        "RestaurantFilial by id: %d not found", id
                )));
        return Optional.of(ConvertDtoUtils.convertRestaurantFilialToDto(restaurantFilial));
    }

    @Override
    public List<RestaurantFilialDto> getAllRestaurantFilial() {
        return restaurantFilialRepository.findAll().stream().map(ConvertDtoUtils::convertRestaurantFilialToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteRestaurantFilialById(Long id) {
        restaurantFilialRepository.deleteById(id);
    }
}
