package com.test.fastFood.utils;

import com.test.fastFood.dto.address.AddressDto;
import com.test.fastFood.dto.address.CityDto;
import com.test.fastFood.dto.address.CountryDto;
import com.test.fastFood.dto.filial.RestaurantFilialDto;
import com.test.fastFood.dto.menu.MenuDto;
import com.test.fastFood.dto.order.OrderDto;
import com.test.fastFood.dto.restaurant.RestaurantDto;
import com.test.fastFood.dto.user.PrivilegeDto;
import com.test.fastFood.dto.user.RoleDto;
import com.test.fastFood.dto.user.UserDto;
import com.test.fastFood.entity.address.Address;
import com.test.fastFood.entity.address.CityEntity;
import com.test.fastFood.entity.address.CountryEntity;
import com.test.fastFood.entity.order.OrderEntity;
import com.test.fastFood.entity.restaurant.MenuEntity;
import com.test.fastFood.entity.restaurant.RestaurantEntity;
import com.test.fastFood.entity.restaurant.RestaurantFilial;
import com.test.fastFood.entity.user.Privilege;
import com.test.fastFood.entity.user.RoleEntity;
import com.test.fastFood.entity.user.UserEntity;
import com.test.fastFood.enums.OrderStatus;

import java.util.stream.Collectors;

public class ConvertDtoUtils {

    public static MenuDto MenuEntityToDto(MenuEntity menu) {
        return MenuDto.builder()
                .name(menu.getName())
                .price(menu.getPrice())
                .cookingTime(menu.getCookingTime())
                .build();
    }

    public static UserDto convertUserToDto(UserEntity user) {
        return UserDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
//                .role(user.getRole())
                .name(user.getUserProfile().getName())
                .surname(user.getUserProfile().getSurname())
                .address(convertAddressToDto(user.getUserProfile().getAddress()))
                .ordersCount(user.getOrders().size())
                .build();
    }

    public static OrderDto convertOrderToDto(OrderEntity order) {
        return OrderDto.builder()
                .username(order.getUser().getUsername())
                .orders(order.getOrderMenuEntities())
                .priceToPay(order.getTotalPrice())
                .totalQuantity(order.getQuantity())
                .orderStatus(OrderStatus.valueOf(order.getInformation().getOrderStatus().name()))
                .createdAt(order.getInformation().getOrderAt())
                .delivery(order.getInformation().getDeliveryTime())
//                .restaurant(order.getInformation().getRestaurantToString())
                .build();
    }

    public static AddressDto convertAddressToDto(Address address) {
        return AddressDto.builder()
                .city(address.getCity().getName())
                .country(address.getCountry().getName())
                .street(address.getStreet())
                .longitude(address.getLongitude())
                .latitude(address.getLatitude())
                .build();
    }

    public static RestaurantDto convertRestaurantToDto(RestaurantEntity restaurant) {
        return RestaurantDto.builder()
                .name(restaurant.getName())
                .build();
    }

    public static RestaurantFilialDto convertRestaurantFilialToDto(RestaurantFilial restaurantFilial) {
        return RestaurantFilialDto.builder()
                .restaurantId(restaurantFilial.getRestaurant().getId())
                .name(restaurantFilial.getName())
                .address(ConvertDtoUtils.convertAddressToDto(restaurantFilial.getAddress()))
                .build();
    }

    public static CityDto convertCityToDto(CityEntity city) {
        return CityDto.builder()
                .name(city.getName())
                .countryId(city.getCountry().getId())
                .build();
    }

    public static CountryDto convertCountryToDto(CountryEntity country) {
        return CountryDto.builder()
                .name(country.getName())
                .build();
    }

    public static PrivilegeDto convertPrivilegeToDto(Privilege privilege) {
        return PrivilegeDto.builder()
                .name(privilege.getName())
                .build();
    }

    public static RoleDto convertRoleToDto(RoleEntity role) {
        return RoleDto.builder()
                .name(role.getName())
                .build();
    }
}
