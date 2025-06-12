package se.amandaflorencia.projektuppgiftits.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/** Klassen är till för att samla nya användarens uppgifter vid registrering
 * Genom validering ser man till att det är i korrekt format
 * Används sedan för att mappas mot en AppUser
 * */

public class UserRegistrationDTO {
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=(?:.*\\d){2,})(?=(?:.*[!@#$%&*]){2,}).*$",
            message = "Password must contain at least 1 uppercase letter, 2 digits, and 2 special characters"
    )
    private String password;
    @NotBlank(message = "Role is required")
    private String role;
    @AssertTrue(message = "You must accept data storage terms")
    private boolean consentGiven;

    public UserRegistrationDTO(String username, String password, String role, boolean consentGiven) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.consentGiven = consentGiven;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isConsentGiven() {
        return consentGiven;
    }

    public void setConsentGiven(boolean consentGiven) {
        this.consentGiven = consentGiven;
    }
}
