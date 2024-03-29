package com.test.fastFood.controller.order;

import com.test.fastFood.dto.address.AddressDto;
import com.test.fastFood.dto.order.OrderBuilder;
import com.test.fastFood.dto.order.OrderCreateDto;
import com.test.fastFood.dto.order.OrderStatusDto;
import com.test.fastFood.enums.OrderStatus;
import com.test.fastFood.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.web.context.WebApplicationContext;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration("classpath:META-INF/web-resources")
@RequiredArgsConstructor
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class OrderControllerTest {
    @Autowired
    WebApplicationContext wac;

    @Autowired
    MockMvc mockMvc;
    WebTestClient client;
    private OrderCreateDto orderCreateDto;
    private final OrderStatusDto orderStatusDto = OrderStatusDto.builder().orderStatus(OrderStatus.PROCESSING).build();

    @BeforeEach
    void setUp() {
        client = MockMvcWebTestClient.bindToApplicationContext(this.wac)
                .configureClient()
                .baseUrl("http://localhost:8080")
                .build();
        AddressDto addressDto = AddressDto.builder()
                .city("Samarkand")
                .country("Uzbekistan")
                .latitude(41.311)
                .longitude(69.240)
                .street("Test")
                .build();

        OrderBuilder orderBuilder = OrderBuilder.builder()
                .menuId(1L)
                .quantity(2)
                .build();

        orderCreateDto = OrderCreateDto.builder()
                .address(addressDto)
                .orderMenu(List.of(orderBuilder))
                .build();

    }

    @Test
    @Order(1)
    @WithMockUser(username = "admin", password = "admin", authorities = "ALL")
    void OrderController_createMethod_ReturnStatusCreatedAndOrderDto() {
        try (MockedStatic<SecurityUtils> utilities = Mockito.mockStatic(SecurityUtils.class)) {
            utilities.when(SecurityUtils::getCurrentUserId).thenReturn(1L);
            client.post().uri("/orders")
                    .body(Mono.just(orderCreateDto), OrderCreateDto.class)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isCreated()
                    .expectBody(OrderCreateDto.class)
                    .returnResult();
        }
    }

    @Test
    @Order(2)
    @WithMockUser(username = "admin", password = "admin", authorities = "ALL")
    void OrderController_getByIdMethod_ReturnStatusOkAndJsonOrderyDto() {
        client.get().uri("/orders/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(OrderCreateDto.class)
                .returnResult();
    }

    @Test
    @Order(3)
    @WithMockUser(username = "admin", password = "admin", authorities = "ALL")
    void OrderController_updateMethod_ReturnStatusCreatedAndOrderDto() {
        client.patch().uri("/orders/1")
                .body(Mono.just(orderStatusDto), OrderStatusDto.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(OrderStatusDto.class)
                .returnResult();
    }

    @Test
    @Order(4)
    @WithMockUser(username = "admin", password = "admin", authorities = "ALL")
    void OrderController_geALLMethod_ReturnStatusOkAndListOrderDto() {
        client.get().uri("/orders")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(List.class)
                .returnResult();
    }

    @Test
    @Order(5)
    @WithMockUser(username = "admin", password = "admin", authorities = "ALL")
    void OrderController_deleteByIdMethod_ReturnStatusNoContent() {
        client.delete().uri("/orders/1")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    @Order(6)
    void OrderController_deleteByIdMethod_ReturnStatusForbidden() throws Exception {
        this.mockMvc
                .perform(delete("/orders/1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("USER"))))
                .andExpect(status().is(403));
    }
}
