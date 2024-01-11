package com.test.fastFood.controller.privilege;

import com.test.fastFood.dto.user.PrivilegeDto;
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
public class PrivilegeControllerTest {
    @Autowired
    WebApplicationContext wac;

    @Autowired
    MockMvc mockMvc;
    WebTestClient client;
    private PrivilegeDto privilegeDto;
    private PrivilegeDto updatedPrivilegeDto;

    @BeforeEach
    void setUp() {
        client = MockMvcWebTestClient.bindToApplicationContext(this.wac)
                .configureClient()
                .baseUrl("http://localhost:8080")
                .build();

        privilegeDto = PrivilegeDto.builder()
                .name("Test")
                .build();

        updatedPrivilegeDto = PrivilegeDto.builder()
                .name("Updated")
                .build();

    }

    @Test
    @Order(1)
    @WithMockUser(username = "admin", password = "admin", authorities = "ALL")
    void PrivilegeControllerTestController_createMethod_PrivilegeControllerTestStatusCreatedAndRPrivilegeControllerTestDto() {
        client.post().uri("/privilege")
                .body(Mono.just(privilegeDto), PrivilegeDto.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(PrivilegeDto.class)
                .returnResult();
    }

    @Test
    @Order(2)
    @WithMockUser(username = "admin", password = "admin", authorities = "ALL")
    void PrivilegeControllerTestController_updateMethod_ReturnStatusCreatedAndPrivilegeControllerTestDto() {
        client.patch().uri("/privilege/1")
                .body(Mono.just(updatedPrivilegeDto), PrivilegeDto.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(PrivilegeDto.class)
                .returnResult();
    }

    @Test
    @Order(3)
    @WithMockUser(username = "admin", password = "admin", authorities = "ALL")
    void PrivilegeControllerTestController_getByIdMethod_ReturnStatusOkAndJsonPrivilegeControllerTestDto() {
        client.get().uri("/privilege/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(PrivilegeDto.class)
                .returnResult();
    }

    @Test
    @Order(4)
    @WithMockUser(username = "admin", password = "admin", authorities = "ALL")
    void PrivilegeControllerTestController_geALLMethod_ReturnStatusOkAndListPrivilegeControllerTestDto() {
        client.get().uri("/privilege")
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
    void PrivilegeControllerTestController_deleteByIdMethod_ReturnStatusNoContent() {
        client.delete().uri("/privilege/1")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    @Order(6)
    void PrivilegeControllerTestController_deleteByIdMethod_ReturnStatusForbidden() throws Exception {
        this.mockMvc
                .perform(delete("/privilege/1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("USER"))))
                .andExpect(status().is(403));
    }
}
