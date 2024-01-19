package com.test.fastFood.service.order;

import com.test.fastFood.dto.address.AddressDto;
import com.test.fastFood.dto.order.OrderBuilder;
import com.test.fastFood.dto.order.OrderCreateDto;
import com.test.fastFood.dto.user.RoleDto;
import com.test.fastFood.dto.user.UserDto;
import com.test.fastFood.entity.address.Address;
import com.test.fastFood.entity.address.CityEntity;
import com.test.fastFood.entity.address.CountryEntity;
import com.test.fastFood.entity.order.OrderEntity;
import com.test.fastFood.entity.order.OrderInformation;
import com.test.fastFood.entity.restaurant.MenuEntity;
import com.test.fastFood.entity.restaurant.RestaurantEntity;
import com.test.fastFood.entity.restaurant.RestaurantFilial;
import com.test.fastFood.entity.user.RoleEntity;
import com.test.fastFood.entity.user.UserEntity;
import com.test.fastFood.entity.user.UserProfile;
import com.test.fastFood.enums.OrderStatus;
import com.test.fastFood.repository.OrderRepository;
import com.test.fastFood.repository.RestaurantRepository;
import com.test.fastFood.security.UserPrincipal;
import com.test.fastFood.service.address.AddressService;
import com.test.fastFood.service.menu.MenuService;
import com.test.fastFood.service.user.UserServiceImpl;
import com.test.fastFood.utils.SecurityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.assertj.core.api.Assertions;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private AddressService addressService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private OrderServiceImpl orderService;
    @Mock
    private UserServiceImpl userService;
    @Mock
    private MenuService menuService;
    @Mock
    private RestaurantRepository restaurantRepository;

    private Address address;
    private UserDto userDto;
    private MenuEntity menuEntity;
    private UserEntity user;
    private OrderCreateDto orderCreateDto;
    private UserPrincipal userPrincipal;
    private RestaurantEntity restaurantEntity;
    private OrderEntity orderEntity;
    private OrderStatus orderStatus;

    @BeforeEach
    public void setUp() {
        userPrincipal = UserPrincipal.builder()
                .userId(1L)
                .build();

        orderStatus = OrderStatus.CANCELED;
        AddressDto addressDto = AddressDto.builder()
                .street("Test")
                .city("Tashkent")
                .country("Uzbekistan")
                .latitude(1.0)
                .longitude(1.0)
                .build();


        CityEntity city = CityEntity.builder()
                .id(1L)
                .name("Samarkand")
                .build();

        CountryEntity country = CountryEntity.builder()
                .id(1L)
                .name("Uzbekistan")
                .build();
        address = Address.builder()
                .id(1L)
                .street("Test")
                .country(country)
                .city(city)
                .latitude(1.0)
                .longitude(1.0)
                .build();

        RoleEntity role = RoleEntity.builder()
                .id(1L)
                .name("Test")
                .build();

        RoleDto roleDto = RoleDto.builder()
                .name("Test")
                .build();

        userDto = UserDto.builder()
                .username("testUsername")
                .name("Test")
                .surname("Test")
                .password("Test")
                .role(roleDto)
                .address(addressDto)
                .build();

        UserProfile userProfile = UserProfile.builder()
                .id(1L)
                .name("Test")
                .surname("TestSurname")
                .user(user)
                .createAt(Instant.now())
                .address(address)
                .build();

        user = UserEntity.builder()
                .userId(1L)
                .username("testUsername")
                .password(passwordEncoder.encode("testPassword"))
                .role(role)
                .isActive(true)
                .userId(1L)
                .userProfile(userProfile)
                .build();


        OrderBuilder orderBuilder = OrderBuilder.builder()
                .menuId(1L)
                .quantity(1)
                .build();

        orderCreateDto = OrderCreateDto.builder()
                .address(addressDto)
                .orderMenu(List.of(
                        orderBuilder
                ))
                .build();


        RestaurantFilial filial = RestaurantFilial.builder()
                .id(1L)
                .name("Test")
                .address(address)
                .build();

        restaurantEntity = RestaurantEntity.builder()
                .id(1L)
                .name("Test")
                .build();
        restaurantEntity.getRestaurantFilial().add(filial);


        menuEntity = MenuEntity.builder()
                .id(1L)
                .name("Test")
                .cookingTime(10)
                .price(10)
                .restaurant(restaurantEntity)
                .build();
        orderEntity = OrderEntity.builder()
                .id(1L)
                .quantity(10)
                .user(user)
                .information(OrderInformation.builder()
                        .address(address)
                        .orderStatus(OrderStatus.NEW)
                        .build())
                .build();

    }

    @Test
    public void OrderService_createOrder_ReturnOrderEntity() {
        try (MockedStatic<SecurityUtils> utilities = Mockito.mockStatic(SecurityUtils.class)) {
            when(addressService.createAddress(userDto.getAddress())).thenReturn(Optional.ofNullable(address));
            utilities.when(SecurityUtils::getCurrentUserId).thenReturn(1L);
            when(menuService.findById(eq(1L))).thenReturn(Optional.of(menuEntity));
            when(restaurantRepository.findById(eq(1L))).thenReturn(Optional.of(restaurantEntity));
            when(userService.getUserById(userPrincipal.getUserId())).thenReturn(Optional.of(user));
            Assertions.assertThat(orderService.createOrder(orderCreateDto).orElseThrow()).isNotNull();
            Assertions.assertThat(orderService.createOrder(orderCreateDto).orElseThrow().getTotalPrice()).isEqualTo(10);
        }
    }

    @Test
    public void OrderService_updateOrder_ReturnOrderEntity() {
        when(orderRepository.findById(eq(1L))).thenReturn(Optional.of(orderEntity));
        Assertions.assertThat(orderService.updateOrder(1L, orderStatus).orElseThrow()).isNotNull();

    }

    @Test
    public void OrderService_geOrderById_ReturnOrderEntity() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderEntity));
        OrderEntity orderEntityTest = orderService.getOrderById(1L).orElseThrow();
        assertAll(
                () -> Assertions.assertThat(orderEntityTest).isNotNull(),
                () -> Assertions.assertThat(orderEntityTest.getTotalPrice()).isEqualTo(orderEntity.getTotalPrice()));
    }

    @Test
    public void OrderService_deleteOrderById_ReturnNon() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderEntity));
        OrderEntity orderEntityTest = orderService.getOrderById(1L).orElseThrow();
        assertAll(
                () -> orderService.deleteOrder(orderEntityTest.getId()));
    }
}
