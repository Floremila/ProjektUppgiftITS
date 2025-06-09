package se.amandaflorencia.projektuppgiftits.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import org.springframework.test.web.servlet.MockMvc;
import se.amandaflorencia.projektuppgiftits.dto.UserRegistrationDTO;
import se.amandaflorencia.projektuppgiftits.service.TokenService;
import se.amandaflorencia.projektuppgiftits.service.UserService;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

                            //Testade först webMvc, men det blev problem med annotationer: mockitoBean och Mockbean.
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @TestConfiguration
    static class TestMockConfig {       //Var tvungen att skapa en testkonfiguration för att inte programmet ska köra de "riktiga".
                                        //hade mycket problem med authenticationManager, fick tillslut byta namn och ha @Primary, så att programmet inte använder sig av den riktiga authenticationManager i SecurityConfig
        @Bean
        public UserService userService() {
            return mock(UserService.class);
        }

        @Bean
        public TokenService tokenService() {
            return mock(TokenService.class);
        }

        @Bean
        @Primary
        public AuthenticationManager mockAuthenticationManager() {
            return mock(AuthenticationManager.class);
        }
    }



    @Test
    void loginTest() throws Exception {
        Authentication mockAuth = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(mockAuth);       //låtsas att autentisering lyckas och returnerar mockat objekt

        when(tokenService.generateToken(mockAuth)).thenReturn("fake token");       //ger tillbaka ett fake-token när controllern försöker skapa Jwt-token

        mockMvc.perform(post("/users/login")
                        .content("{ \"username\": \"Amanda\", \"password\": \"Password123!\" }")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("fake token"));
    }


    @Test
    void registerUserTest() throws Exception {
        UserRegistrationDTO dto = new UserRegistrationDTO(
                "Amanda",
                "Password123!!",
                "USER",
                true
        );

        String jsonBody = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(content().string("User successfully created"));


    }


}