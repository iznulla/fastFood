package com.test.fastFood.controller.menu;

import com.test.fastFood.dto.menu.MenuDto;
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
public class MenuControllerTest {
        @Autowired
        WebApplicationContext wac;

        @Autowired
        MockMvc mockMvc;
        WebTestClient client;
        private MenuDto menuDto;

        @BeforeEach
        void setUp() {
                client = MockMvcWebTestClient.bindToApplicationContext(this.wac)
                                .configureClient()
                                .baseUrl("http://localhost:8080")
                                .build();

                menuDto = MenuDto.builder()
                                .name("Test")
                                .restaurantId(1L)
                                .price(10)
                                .cookingTime(5)
                                .build();
        }

        @Test
        @Order(1)
        @WithMockUser(username = "admin", password = "admin", authorities = "ALL")
        void MenuController_createMethod_ReturnStatusCreatedAndMenuDto() {
                client.post().uri("/menu")
                                .body(Mono.just(menuDto), MenuDto.class)
                                .accept(MediaType.APPLICATION_JSON)
                                .exchange()
                                .expectStatus().isCreated()
                                .expectBody(MenuDto.class)
                                .returnResult();
        }

        @Test
        @Order(2)
        @WithMockUser(username = "admin", password = "admin", authorities = "ALL")
        void MenuController_getByIdMethod_ReturnStatusOkAndJsonMenuyDto() {
                client.get().uri("/menu/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .exchange()
                        .expectStatus().isOk()
                        .expectHeader().contentType(MediaType.APPLICATION_JSON)
                        .expectBody(MenuDto.class)
                        .returnResult();
        }

        @Test
        @Order(3)
        @WithMockUser(username = "admin", password = "admin", authorities = "ALL")
        void MenuController_updateMethod_ReturnStatusCreatedAndMenuDto() {
                client.patch().uri("/menu/2")
                                .body(Mono.just(menuDto), MenuDto.class)
                                .accept(MediaType.APPLICATION_JSON)
                                .exchange()
                                .expectStatus().isCreated()
                                .expectBody(MenuDto.class)
                                .returnResult();
        }

        @Test
        @Order(4)
        @WithMockUser(username = "admin", password = "admin", authorities = "ALL")
        void MenuController_geALLMethod_ReturnStatusOkAndListMenuDto() {
                client.get().uri("/menu")
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
        void MenuController_deleteByIdMethod_ReturnStatusNoContent() {
                client.delete().uri("/menu/2")
                                .exchange()
                                .expectStatus().isNoContent();
        }

        @Test
        @Order(6)
        void MenuController_deleteByIdMethod_ReturnStatusForbidden() throws Exception {
                this.mockMvc
                                .perform(delete("/menu/1")
                                                .with(jwt().authorities(new SimpleGrantedAuthority("USER"))))
                                .andExpect(status().is(403));
        }
}
