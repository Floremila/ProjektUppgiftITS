package se.amandaflorencia.projektuppgiftits.dto;

/**
 * DTO used for login requests.
 *
 * Contains the username and password provided by the user when logging in.
 */
public record JwtLoginRequest(String username, String password) {

}
