package com.test.fastFood.controller.country;

import com.test.fastFood.dto.address.CountryDto;
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
public class CountryControllerTest {
    @Autowired
    WebApplicationContext wac;

    @Autowired
    MockMvc mockMvc;
    WebTestClient client;
    private CountryDto countryDto;

    @BeforeEach
    void setUp() {
        client = MockMvcWebTestClient.bindToApplicationContext(this.wac)
                .configureClient()
                .baseUrl("http://localhost:8080")
                .build();

        countryDto = CountryDto.builder()
                        .name("Test")
                        .build();
    }

    @Test
    @Order(1)
    @WithMockUser(username = "admin", password = "admin", authorities = "ALL")
    void CountryController_createMethod_ReturnStatusCreatedAndCountryDto() {
        client.post().uri("/country")
                .body(Mono.just(countryDto), CountryDto.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CountryDto.class)
                .returnResult();
    }

    @Test
    @Order(2)
    @WithMockUser(username = "admin", password = "admin", authorities = "ALL")
    void CountryController_updateMethod_ReturnStatusCreatedAndCountryDto() {
        client.patch().uri("/country/3")
                .body(Mono.just(countryDto), CountryDto.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CountryDto.class)
                .returnResult();
    }

    @Test
    @Order(3)
    @WithMockUser(username = "admin", password = "admin", authorities = "ALL")
    void CountryController_getByIdMethod_ReturnStatusOkAndJsonCountryDto() {
        client.get().uri("/country/3")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(CountryDto.class)
                .returnResult();
    }

    @Test
    @Order(4)
    @WithMockUser(username = "admin", password = "admin", authorities = "ALL")
    void CountryController_geALLMethod_ReturnStatusOkAndListCountryDto() {
        client.get().uri("/country")
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
    void CountryController_deleteByIdMethod_ReturnStatusNoContent() {
        client.delete().uri("/country/3")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    @Order(6)
    void CountryController_deleteByIdMethod_ReturnStatusForbidden() throws Exception {
        this.mockMvc
                .perform(delete("/country/1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("USER"))))
                .andExpect(status().is(403));
    }


}
