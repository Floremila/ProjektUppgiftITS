package se.amandaflorencia.projektuppgiftits.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import se.amandaflorencia.projektuppgiftits.model.AppUser;
import se.amandaflorencia.projektuppgiftits.repository.UserRepository;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final ObjectMapper mapper = new ObjectMapper();

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

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldReturnUsersListWhenUsersExist() throws Exception {
        mockMvc.perform(get("/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[?(@.username=='admin')]").exists())
                .andExpect(jsonPath("$[?(@.username=='user')]").exists());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldReturnNoContentWhenNoUsers() throws Exception {
        userRepository.deleteAll();

        mockMvc.perform(get("/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void shouldForbidNonAdminOnGetAll() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldDeleteUserWhenAdmin() throws Exception {
        AppUser toDelete = new AppUser();
        toDelete.setUsername("toDelete");
        toDelete.setPassword(passwordEncoder.encode("Password123!!"));
        toDelete.setRole("USER");
        toDelete.setConsentGiven(true);
        AppUser saved = userRepository.save(toDelete);

        mockMvc.perform(delete("/user/{id}", saved.getId()))
                .andExpect(status().isNoContent());

        Optional<AppUser> opt = userRepository.findById(saved.getId());
        assertTrue(opt.isEmpty(), "User should be removed from the database");
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void shouldForbidNonAdminOnDelete() throws Exception {
        mockMvc.perform(delete("/user/{id}", 1L))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldUpdateUserWhenAdmin() throws Exception {
        AppUser original = new AppUser();
        original.setUsername("updateMe");
        original.setPassword(passwordEncoder.encode("Password123!!"));
        original.setRole("USER");
        original.setConsentGiven(false);
        AppUser saved = userRepository.save(original);

        AppUser updateDto = new AppUser();
        updateDto.setUsername("updatedName");
        updateDto.setPassword(passwordEncoder.encode("NewPass123!!"));
        updateDto.setRole("ADMIN");
        updateDto.setConsentGiven(true);

        String json = mapper.writeValueAsString(updateDto);

        mockMvc.perform(put("/user/{id}", saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("updatedName"))
                .andExpect(jsonPath("$.role").value("ADMIN"))
                .andExpect(jsonPath("$.consentGiven").value(true));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void shouldForbidNonAdminOnUpdate() throws Exception {
        AppUser dto = new AppUser();
        dto.setUsername("x");
        String json = mapper.writeValueAsString(dto);

        mockMvc.perform(put("/user/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden());
    }
}
