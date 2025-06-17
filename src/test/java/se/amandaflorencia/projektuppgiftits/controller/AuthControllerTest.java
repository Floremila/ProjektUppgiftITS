package se.amandaflorencia.projektuppgiftits.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import se.amandaflorencia.projektuppgiftits.dto.UserRegistrationDTO;
import se.amandaflorencia.projektuppgiftits.model.AppUser;
import se.amandaflorencia.projektuppgiftits.repository.UserRepository;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.emptyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    ObjectMapper mapper = new ObjectMapper(); //konverterar javaobj till json

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        AppUser admin = new AppUser();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("Password123!!"));
        admin.setRole("ADMIN");
        admin.setConsentGiven(true);

        userRepository.save(admin);

        AppUser user = new AppUser();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("Password123!!"));
        user.setRole("USER");
        user.setConsentGiven(true);

        userRepository.save(user);
    }

    private String getToken(String username, String password) throws Exception{
        MvcResult result = mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\""+username+"\",\"password\":\""+password+"\"}"))
                .andExpect(status().isOk())
                .andReturn();

        return result.getResponse().getContentAsString();
    }

    @Test
    void givenValidCredentials_whenLogin_thenReturnStatusOk() throws Exception {
        mockMvc.perform(post("/users/login")
                        .content("{ \"username\": \"user\", \"password\": \"Password123!!\" }")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.not(emptyString())));
    }

    @Test
    void givenInvalidCredentials_whenLogin_thenReturnUnauthorized() throws Exception{
        mockMvc.perform(post("/users/login")
                .content("{\"username\": \"user\", \"password\": \"Fel\" }")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }


    @Test
    void givenAdminToken_whenRegister_thenReturnStatusOk() throws Exception {
        String adminToken = getToken("admin", "Password123!!");
        UserRegistrationDTO dto = new UserRegistrationDTO(
                "NewUser",
                "Password123!!",
                "USER",
                true
        );

        String jsonBody = mapper.writeValueAsString(dto);

        mockMvc.perform(post("/users/register")
                        .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(content().string("User successfully created"));
    }


    @Test
    void givenAdminToken_andInvalidCredentials_whenRegister_thenReturnBadRequest() throws Exception{
        String adminToken = getToken("admin", "Password123!!");
        UserRegistrationDTO dto = new UserRegistrationDTO(
                "User",
                "FelFormat",
                "USER",
                true
        );

        String jsonBody = mapper.writeValueAsString(dto);

        mockMvc.perform(post("/users/register")
                        .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isBadRequest());

    }

    @Test
    void givenUserToken_whenRegister_thenReturnForbidden() throws Exception {
        String userToken = getToken("user", "Password123!!");

        UserRegistrationDTO dto = new UserRegistrationDTO(
                "forbiddenUser",
                "Password123!!",
                "USER",
                true
        );

        String jsonBody = mapper.writeValueAsString(dto);

        mockMvc.perform(post("/users/register")
                .header("Authorization", "Bearer " + userToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isForbidden());

    }


}