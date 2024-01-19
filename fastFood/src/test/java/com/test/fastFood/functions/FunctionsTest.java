package com.test.fastFood.functions;

import com.test.fastFood.dto.address.AddressDto;
import com.test.fastFood.dto.order.OrderBuilder;
import com.test.fastFood.dto.order.OrderCreateDto;
import com.test.fastFood.dto.user.RoleDto;
import com.test.fastFood.dto.user.UserDto;
import com.test.fastFood.entity.address.Address;
import com.test.fastFood.entity.address.CityEntity;
import com.test.fastFood.entity.address.CountryEntity;
import com.test.fastFood.entity.order.OrderEntity;
import com.test.fastFood.entity.restaurant.MenuEntity;
import com.test.fastFood.entity.restaurant.RestaurantEntity;
import com.test.fastFood.entity.restaurant.RestaurantFilial;
import com.test.fastFood.entity.user.RoleEntity;
import com.test.fastFood.entity.user.UserEntity;
import com.test.fastFood.entity.user.UserProfile;
import com.test.fastFood.exception.ResourceNotFoundException;
import com.test.fastFood.repository.OrderRepository;
import com.test.fastFood.repository.RestaurantRepository;
import com.test.fastFood.security.UserPrincipal;
import com.test.fastFood.service.address.AddressService;
import com.test.fastFood.service.menu.MenuService;
import com.test.fastFood.service.order.OrderServiceImpl;
import com.test.fastFood.service.user.UserServiceImpl;
import com.test.fastFood.utils.SecurityUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FunctionsTest {
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
    Address furtherAddress;
    Address nearAddress;

    MenuEntity donerMenuEntity;
    MenuEntity burgerMenuEntity;
    private UserDto userDto;
    private UserEntity user;
    private OrderCreateDto orderCreateDto;
    private UserPrincipal userPrincipal;
    private RestaurantEntity restaurantEntity;

    AddressDto addressDto;

    AddressDto furtherAddressDto;

    AddressDto nearAddressDto;

    RestaurantFilial furtherFilial;

    RestaurantFilial nearFilial;

    @BeforeEach
    public void setUp() {
        userPrincipal = UserPrincipal.builder()
                .userId(1L)
                .build();
        addressDto = AddressDto.builder()
                .street("IT Park")
                .city("Tashkent")
                .country("Uzbekistan")
                .latitude(41.34101239392938)
                .longitude(69.33599271527646)
                .build();

        furtherAddressDto = AddressDto.builder()
                .street("Yuzrabot")
                .city("Tashkent")
                .country("Uzbekistan")
                .latitude(41.35748363693251)
                .longitude(69.37662189241189)
                .build();

        nearAddressDto = AddressDto.builder()
                .street("Ekobazar")
                .city("Tashkent")
                .country("Uzbekistan")
                .latitude(41.34860798652945)
                .longitude(69.35792002223815)
                .build();


        CityEntity city = CityEntity.builder()
                .id(1L)
                .name("Samarkand")
                .build();

        CountryEntity country = CountryEntity.builder()
                .id(1L)
                .name("Uzbekistan")
                .build();
        address = Address.builder().street("IT Park")
                .city(city)
                .country(country)
                .latitude(41.34101239392938)
                .longitude(69.33599271527646)
                .build();

        furtherAddress = Address.builder()
                .street("Yuzrabot")
                .city(city)
                .country(country)
                .latitude(41.35748363693251)
                .longitude(69.37662189241189)
                .build();

        nearAddress = Address.builder()
                .street("Ekobazar")
                .city(city)
                .country(country)
                .latitude(41.34860798652945)
                .longitude(69.35792002223815)
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


        OrderBuilder orderBuilderDoner = OrderBuilder.builder()
                .menuId(1L)
                .quantity(1)
                .build();

        OrderBuilder orderBuilderBurger = OrderBuilder.builder()
                .menuId(2L)
                .quantity(1)
                .build();

        orderCreateDto = OrderCreateDto.builder()
                .address(addressDto)
                .orderMenu(List.of(
                        orderBuilderDoner, orderBuilderBurger
                ))
                .build();


        furtherFilial = RestaurantFilial.builder()
                .id(1L)
                .name("EVOS Yuzrabot")
                .address(furtherAddress)
                .build();

        nearFilial = RestaurantFilial.builder()
                .id(1L)
                .name("EVOS Ecobozor")
                .address(nearAddress)
                .build();

        restaurantEntity = RestaurantEntity.builder()
                .id(1L)
                .name("EVOS")
                .build();
        restaurantEntity.getRestaurantFilial().addAll(List.of(furtherFilial, nearFilial));


        donerMenuEntity = MenuEntity.builder()
                .id(1L)
                .name("Doner Kebab")
                .cookingTime(4)
                .price(24000)
                .restaurant(restaurantEntity)
                .build();

        burgerMenuEntity = MenuEntity.builder()
                .id(2L)
                .name("Burger Kebab")
                .cookingTime(4)
                .price(56000)
                .restaurant(restaurantEntity)
                .build();

    }

    /**
     * Test case for the OrderService_createOrder_ReturnOrderEntity function.
     */
    @Test
    public void OrderFunction_createOrder_ReturnOrderEntity() {
        try (MockedStatic<SecurityUtils> utilities = Mockito.mockStatic(SecurityUtils.class)) {
            when(addressService.createAddress(userDto.getAddress())).thenReturn(Optional.ofNullable(address));
            utilities.when(SecurityUtils::getCurrentUserId).thenReturn(1L);
            when(menuService.findById(eq(1L))).thenReturn(Optional.of(donerMenuEntity));
            when(menuService.findById(eq(2L))).thenReturn(Optional.of(burgerMenuEntity));
            when(restaurantRepository.findById(eq(1L))).thenReturn(Optional.of(restaurantEntity));
            when(userService.getUserById(userPrincipal.getUserId())).thenReturn(Optional.of(user));
            Assertions.assertThat(orderService.createOrder(orderCreateDto).orElseThrow()).isNotNull();
        }
    }

    @Test
    public void OrderFunction_checkChoosingFilialOnOrder_ReturnNearFilial() {
        try (MockedStatic<SecurityUtils> utilities = Mockito.mockStatic(SecurityUtils.class)) {
            when(addressService.createAddress(userDto.getAddress())).thenReturn(Optional.ofNullable(address));
            utilities.when(SecurityUtils::getCurrentUserId).thenReturn(1L);
            when(menuService.findById(eq(1L))).thenReturn(Optional.of(donerMenuEntity));
            when(menuService.findById(eq(2L))).thenReturn(Optional.of(burgerMenuEntity));
            when(restaurantRepository.findById(eq(1L))).thenReturn(Optional.of(restaurantEntity));
            when(userService.getUserById(userPrincipal.getUserId())).thenReturn(Optional.of(user));
            Assertions.assertThat(orderService.createOrder(orderCreateDto).orElseThrow()).isNotNull();
            Assertions.assertThat(orderService.createOrder(orderCreateDto).orElseThrow()
                    .getInformation().getRestaurantFilial().getName()).isEqualTo("EVOS Ecobozor");
            Assertions.assertThat(orderService.createOrder(orderCreateDto).orElseThrow()
                    .getInformation().getRestaurantFilial()).isEqualTo(nearFilial);
        }
    }

    @Test
    public void OrderFunction_CheckDeliveryTime() {
        try (MockedStatic<SecurityUtils> utilities = Mockito.mockStatic(SecurityUtils.class)) {
            when(addressService.createAddress(userDto.getAddress())).thenReturn(Optional.ofNullable(address));
            utilities.when(SecurityUtils::getCurrentUserId).thenReturn(1L);
            when(menuService.findById(eq(1L))).thenReturn(Optional.of(donerMenuEntity));
            when(menuService.findById(eq(2L))).thenReturn(Optional.of(burgerMenuEntity));
            when(restaurantRepository.findById(eq(1L))).thenReturn(Optional.of(restaurantEntity));
            when(userService.getUserById(userPrincipal.getUserId())).thenReturn(Optional.of(user));
            Assertions.assertThat(orderService.createOrder(orderCreateDto).orElseThrow()).isNotNull();

            OrderEntity orderTestTwoMenu = orderService.createOrder(orderCreateDto).orElseThrow();
            Long deliveryTime = ChronoUnit.MINUTES.between(orderTestTwoMenu.getInformation().getOrderAt(), orderTestTwoMenu.getInformation().getDeliveryTime()
            );
            Assertions.assertThat(deliveryTime).isLessThanOrEqualTo(15);
            Assertions.assertThat(deliveryTime).isGreaterThanOrEqualTo(13);

            orderCreateDto.setOrderMenu(List.of(OrderBuilder.builder()
                    .menuId(1L)
                    .quantity(1)
                    .build()));
            OrderEntity orderTestOneMenu = orderService.createOrder(orderCreateDto).orElseThrow();
            deliveryTime = ChronoUnit.MINUTES.between(orderTestOneMenu.getInformation().getOrderAt(), orderTestOneMenu.getInformation().getDeliveryTime()
            );
            Assertions.assertThat(deliveryTime).isLessThanOrEqualTo(15);
            Assertions.assertThat(deliveryTime).isGreaterThanOrEqualTo(13);

            orderCreateDto.setOrderMenu(List.of(
                    OrderBuilder.builder()
                            .menuId(1L)
                            .quantity(1)
                            .build(),
                    OrderBuilder.builder()
                            .menuId(2L)
                            .quantity(5)
                            .build())
            );
            OrderEntity orderTestFiveMenu = orderService.createOrder(orderCreateDto).orElseThrow();
             deliveryTime = ChronoUnit.MINUTES.between(orderTestFiveMenu.getInformation().getOrderAt(), orderTestFiveMenu.getInformation().getDeliveryTime()
            );
            Assertions.assertThat(deliveryTime).isLessThanOrEqualTo(16);
            Assertions.assertThat(deliveryTime).isGreaterThanOrEqualTo(14);
        }
    }

    @Test
    public void OrderFunction_CheckTotalPriceFromMenu() {
        try (MockedStatic<SecurityUtils> utilities = Mockito.mockStatic(SecurityUtils.class)) {
            when(addressService.createAddress(userDto.getAddress())).thenReturn(Optional.ofNullable(address));
            utilities.when(SecurityUtils::getCurrentUserId).thenReturn(1L);
            when(menuService.findById(eq(1L))).thenReturn(Optional.of(donerMenuEntity));
            when(menuService.findById(eq(2L))).thenReturn(Optional.of(burgerMenuEntity));
            when(restaurantRepository.findById(eq(1L))).thenReturn(Optional.of(restaurantEntity));
            when(userService.getUserById(userPrincipal.getUserId())).thenReturn(Optional.of(user));
            Assertions.assertThat(orderService.createOrder(orderCreateDto).orElseThrow()).isNotNull();

            OrderEntity orderTestTwoMenu = orderService.createOrder(orderCreateDto).orElseThrow();
            Assertions.assertThat(orderTestTwoMenu.getTotalPrice()).isEqualTo(80000);

            orderCreateDto.setOrderMenu(List.of(OrderBuilder.builder()
                    .menuId(1L)
                    .quantity(1)
                    .build()));
            OrderEntity orderTestOneMenu = orderService.createOrder(orderCreateDto).orElseThrow();
            Assertions.assertThat(orderTestOneMenu.getTotalPrice()).isEqualTo(24000);

            orderCreateDto.setOrderMenu(List.of(
                    OrderBuilder.builder()
                            .menuId(1L)
                            .quantity(1)
                            .build(),
                    OrderBuilder.builder()
                            .menuId(2L)
                            .quantity(5)
                            .build())
            );
            OrderEntity orderTestFiveMenu = orderService.createOrder(orderCreateDto).orElseThrow();
            Assertions.assertThat(orderTestFiveMenu.getTotalPrice()).isEqualTo(304000);
        }
    }

    @Test
    public void OrderFunction_checkChoosingFilialOnOrder_checkFail() {
        try (MockedStatic<SecurityUtils> utilities = Mockito.mockStatic(SecurityUtils.class)) {
            when(addressService.createAddress(userDto.getAddress())).thenReturn(Optional.ofNullable(address));
            utilities.when(SecurityUtils::getCurrentUserId).thenReturn(1L);
            when(menuService.findById(eq(1L))).thenReturn(Optional.of(donerMenuEntity));
            when(menuService.findById(eq(2L))).thenReturn(Optional.of(burgerMenuEntity));
            when(restaurantRepository.findById(eq(1L))).thenReturn(Optional.of(restaurantEntity));
            when(userService.getUserById(userPrincipal.getUserId())).thenReturn(Optional.of(user));
            Assertions.assertThat(orderService.createOrder(orderCreateDto).orElseThrow()).isNotNull();
            Assertions.assertThat(orderService.createOrder(orderCreateDto).orElseThrow()
                    .getInformation().getRestaurantFilial().getName()).isNotEqualTo("EVOS Yuzrabot");
            Assertions.assertThat(orderService.createOrder(orderCreateDto).orElseThrow()
                    .getInformation().getRestaurantFilial()).isNotEqualTo(furtherFilial);
        }
    }

    @Test
    public void OrderFunction_checkChoosingFilialOnOrder_checkThrow() {
        try (MockedStatic<SecurityUtils> utilities = Mockito.mockStatic(SecurityUtils.class)) {
            when(addressService.createAddress(userDto.getAddress())).thenReturn(Optional.ofNullable(address));
            utilities.when(SecurityUtils::getCurrentUserId).thenReturn(1L);
            when(menuService.findById(eq(1L))).thenReturn(Optional.of(donerMenuEntity));
            when(userService.getUserById(userPrincipal.getUserId())).thenReturn(Optional.of(user));
            assertAll(
                    () -> Assertions.assertThatThrownBy(() -> {
                                orderService.createOrder(orderCreateDto).orElseThrow();
                            })
                            .isInstanceOf(ResourceNotFoundException.class)
                            .hasMessage("Menu with id 2 not found")
            );
            doReturn(Optional.empty()).when(menuService).findById(eq(1L));
            assertAll(
                    () -> Assertions.assertThatThrownBy(() -> {
                                orderService.createOrder(orderCreateDto).orElseThrow();
                            })
                            .isInstanceOf(ResourceNotFoundException.class)
                            .hasMessage("Menu with id 1 not found")
            );
        }
    }
}
