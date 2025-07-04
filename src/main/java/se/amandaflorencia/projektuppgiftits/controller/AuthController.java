package se.amandaflorencia.projektuppgiftits.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.amandaflorencia.projektuppgiftits.dto.JwtLoginRequest;
import se.amandaflorencia.projektuppgiftits.dto.UserRegistrationDTO;
import se.amandaflorencia.projektuppgiftits.service.TokenService;
import se.amandaflorencia.projektuppgiftits.service.UserService;
import se.amandaflorencia.projektuppgiftits.util.LoggingComponent;

/** REST-kontroller för autentisering
 * Har olika endpoints för att registrera användare och logga in
 * Använder sig av userService för tillgång till databas, authenticationManager och TokenService
 * för autentisering
 * */

@Tag(
        name = "Authentication",
        description = "Endpoints for user registration and login using JWT"
)
@RestController
@RequestMapping("/users")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final LoggingComponent loggingComponent;

    public AuthController(UserService userService,
                          AuthenticationManager authenticationManager,
                          TokenService tokenService,
                          LoggingComponent loggingComponent) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.loggingComponent = loggingComponent;
    }

    /** Metoden registrerar en ny användare, enbart för användare med admin-roll
     * @param userRegistrationDTO tar in validerade användaruppgifter för en ny användare
     * @return ResponseEntity med statuskod 200ok om användare skapats
     * */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        userService.registerUser(userRegistrationDTO);
        // registration is already logged in the UserService via LoggingComponent
        return ResponseEntity.ok("User successfully created");
    }

    /** Metod för autentisering och inloggning, returnerar en jwt
     * @param jwtLoginRequest tar emot användarnamn och lösenord via jwt
     * @return ResponseEntity med statuskod 200 OK samt jwt-token om lyckad inloggning
     *
     * */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody JwtLoginRequest jwtLoginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        jwtLoginRequest.username(),
                        jwtLoginRequest.password()
                )
        );

        String token = tokenService.generateToken(authentication);
        // log successful login
        logger.info("User '{}' logged in successfully", authentication.getName());
        return ResponseEntity.ok(token);
    }
}
