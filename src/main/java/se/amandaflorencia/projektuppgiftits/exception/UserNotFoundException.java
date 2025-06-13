package se.amandaflorencia.projektuppgiftits.exception;

/**
 * Custom exception thrown when a user with a given ID is not found.
 */

public class UserNotFoundException extends RuntimeException {

    /**
     * Creates a new exception with a custom error message.
     *
     * @param message the error message to show.
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
