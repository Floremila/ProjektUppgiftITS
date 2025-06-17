package se.amandaflorencia.projektuppgiftits.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Handles exceptions globally for the whole application.
 *
 * Right now it only handles UserNotFoundException and returns a 404 response.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles the case when a user is not found.
     *
     * @param ex the exception that was thrown.
     * @return a 404 response with the error message.
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}

