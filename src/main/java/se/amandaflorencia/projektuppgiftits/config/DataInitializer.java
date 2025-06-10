package se.amandaflorencia.projektuppgiftits.config;

import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import se.amandaflorencia.projektuppgiftits.dto.UserRegistrationDTO;
import se.amandaflorencia.projektuppgiftits.service.UserService;

@Component
public class DataInitializer {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        boolean userExists = userService.getAllUsers().stream()
                .anyMatch(user -> "admin".equals(user.getUsername()));

        if (!userExists){
            UserRegistrationDTO adminDto = new UserRegistrationDTO(
                    "admin",
                    passwordEncoder.encode("Password123!!"),
                    "ADMIN",
                    true
                    );

            userService.registerUser((adminDto));
        }
    }

}
