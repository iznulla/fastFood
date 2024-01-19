package com.test.fastFood.controller.user;

import com.test.fastFood.dto.address.AddressDto;
import com.test.fastFood.dto.user.PrivilegeDto;
import com.test.fastFood.dto.user.RoleDto;
import com.test.fastFood.dto.user.UserDto;
import com.test.fastFood.entity.user.UserProfile;
import com.test.fastFood.service.email.EmailServiceImpl;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration("classpath:META-INF/web-resources")
@RequiredArgsConstructor
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class UserControllerTest {
    @Autowired
    WebApplicationContext wac;

    @Autowired
    MockMvc mockMvc;

    WebTestClient client;
    private UserDto userDto;
    private UserProfile userProfile;

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


        RoleDto roleDto = RoleDto.builder()
                .name("ADMIN")
                .build();


        userDto = UserDto.builder()
                .username("test")
                .address(addressDto)
                .name("test")
                .password("test")
                .surname("test")
                .role(roleDto)
                .build();


    }

    @Test
    @Order(1)
    @WithMockUser(username = "admin", password = "admin", authorities = "ALL")
    void UserController_createMethod_ReturnStatusCreatedAndUserDto() {
        EmailServiceImpl emailServiceMock = mock(EmailServiceImpl.class);
        when(emailServiceMock.sendSimpleMessage(anyString(), anyString(), anyString())).thenReturn(true);
        client.post().uri("/users")
                .body(Mono.just(userDto), UserDto.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserDto.class)
                .returnResult();

    }

    @Test
    @Order(2)
    @WithMockUser(username = "admin", password = "admin", authorities = "ALL")
    void UserController_getByIdMethod_ReturnStatusOkAndJsonUseryDto() {
        client.get().uri("/users/2")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(UserDto.class)
                .returnResult();
    }

    @Test
    @Order(3)
    @WithMockUser(username = "admin", password = "admin", authorities = "ALL")
    void UserController_updateMethod_ReturnStatusCreatedAndUserDto() {
        client.patch().uri("/users/2")
                .body(Mono.just(userDto), UserDto.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserDto.class)
                .returnResult();
    }

    @Test
    @Order(4)
    @WithMockUser(username = "admin", password = "admin", authorities = "ALL")
    void UserController_geALLMethod_ReturnStatusOkAndListUserDto() {
        client.get().uri("/users")
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
    void UserController_deleteByIdMethod_ReturnStatusNoContent() {
        client.delete().uri("/users/2")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    @Order(6)
    void UserController_deleteByIdMethod_ReturnStatusForbidden() throws Exception {
        this.mockMvc
                .perform(delete("/users/2")
                        .with(jwt().authorities(new SimpleGrantedAuthority("USER"))))
                .andExpect(status().is(403));
    }
}
