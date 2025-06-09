package se.amandaflorencia.projektuppgiftits.controller;

import org.junit.jupiter.api.Test;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.web.servlet.MockMvc;
import se.amandaflorencia.projektuppgiftits.service.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @TestConfiguration
    static class TestMockConfig {

        @Bean
        @Primary
        public UserService userService() {
            return mock(UserService.class);
        }
    }

    @Test
    void deleteUser() throws Exception{
        mockMvc.perform(
                delete("/user/{id}", 1L)
                .with(user("admin").password("hemligt").roles("ADMIN"))
                )
                .andExpect(status().isNoContent());
    }
}