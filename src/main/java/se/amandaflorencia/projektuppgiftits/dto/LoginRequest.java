package se.amandaflorencia.projektuppgiftits.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

//Denna DTO är till för inloggning
// Denna DTO är till för inloggning via formulär med validering (används ej i JWT/REST-flödet)


public class LoginRequest {
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=(?:.*\\d){2,})(?=(?:.*[!@#$%&*]){2,}).*$",
            message = "Password must contain at least 1 uppercase letter, 2 digits, and 2 special characters"
    )
    private String password;

}
