package se.amandaflorencia.projektuppgiftits.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.amandaflorencia.projektuppgiftits.dto.UserRegistrationDTO;
import se.amandaflorencia.projektuppgiftits.service.UserService;


//Här har vi en controller för registering av ny användare och logga in


@RestController
@RequestMapping("/users")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        userService.registerUser(userRegistrationDTO);
        return ResponseEntity.ok("User successfully created");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok("");
    }
}
