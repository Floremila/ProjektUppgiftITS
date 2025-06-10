package se.amandaflorencia.projektuppgiftits.config;

import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import se.amandaflorencia.projektuppgiftits.dto.UserRegistrationDTO;
import se.amandaflorencia.projektuppgiftits.service.UserService;

@Component
public class DataInitializer {

    private final UserService userService;

    public DataInitializer(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        boolean userExists = userService.getAllUsers().stream()
                .anyMatch(user -> "admin".equals(user.getUsername()));

        if (!userExists){
            UserRegistrationDTO adminDto = new UserRegistrationDTO(
                    "admin",
                    "Password123!!",
                    "ADMIN",
                    true
                    );

            userService.registerUser((adminDto));
        }
    }

}
