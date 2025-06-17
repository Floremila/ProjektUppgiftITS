package se.amandaflorencia.projektuppgiftits.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Utility component for logging user-related actions.
 *
 * Used by the service layer to log events like registration and deletion.
 */
@Component
public class LoggingComponent {

    private static final Logger logger = LoggerFactory.getLogger(LoggingComponent.class);

    /**
     * Logs when a new user has registered.
     *
     * @param username the username of the registered user.
     */
    public void logUserRegistration(String username) {

        logger.info("New user registered: {}", username);
    }

    /**
     * Logs when a user has been deleted.
     *
     * @param userId the ID of the deleted user.
     */
    public void logUserDeletion(Long userId) {

        logger.info("User with ID {} has been deleted", userId);
    }
}
