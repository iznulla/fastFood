package com.test.fastFood.controller.filial;

import com.test.fastFood.dto.address.AddressDto;
import com.test.fastFood.dto.filial.RestaurantFilialDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
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
public class RestaurantFilialControllerTest {
    @Autowired
    WebApplicationContext wac;

    @Autowired
    MockMvc mockMvc;
    WebTestClient client;
    private RestaurantFilialDto restaurantFilialDto;

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

        restaurantFilialDto = RestaurantFilialDto.builder()
                .restaurantId(1L)
                .name("Test")
                .address(addressDto)
                .build();
    }

    @Test
    @Order(1)
    @WithMockUser(username = "admin", password = "admin", authorities = "ALL")
    void ResFilController_createMethod_RetFilStatusCreatedAndRestFilDto() {
        client.post().uri("/filial")
                .body(Mono.just(restaurantFilialDto), RestaurantFilialDto.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(RestaurantFilialDto.class)
                .returnResult();
    }

    @Test
    @Order(2)
    @WithMockUser(username = "admin", password = "admin", authorities = "ALL")
    void ResFilController_updateMethod_ReturnStatusCreatedAndResFilDto() {
        client.patch().uri("/filial/1")
                .body(Mono.just(restaurantFilialDto), RestaurantFilialDto.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(RestaurantFilialDto.class)
                .returnResult();
    }

    @Test
    @Order(3)
    @WithMockUser(username = "admin", password = "admin", authorities = "ALL")
    void ResFilController_getByIdMethod_ReturnStatusOkAndJsonResFilDto() {
        client.get().uri("/filial/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(RestaurantFilialDto.class)
                .returnResult();
    }

    @Test
    @Order(4)
    @WithMockUser(username = "admin", password = "admin", authorities = "ALL")
    void ResFilController_geALLMethod_ReturnStatusOkAndListResFilDto() {
        client.get().uri("/filial")
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
    void ResFilController_deleteByIdMethod_ReturnStatusNoContent() {
        client.delete().uri("/filial/1")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    @Order(6)
    void ResFilController_deleteByIdMethod_ReturnStatusForbidden() throws Exception {
        this.mockMvc
                .perform(delete("/filial/1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("USER"))))
                .andExpect(status().is(403));
    }

}
