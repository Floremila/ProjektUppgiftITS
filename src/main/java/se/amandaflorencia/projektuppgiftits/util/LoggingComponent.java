package se.amandaflorencia.projektuppgiftits.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggingComponent {

    private static final Logger logger = LoggerFactory.getLogger(LoggingComponent.class);

    public void logUserRegistration(String username) {
        logger.info("New user registered: {}", username);
    }

    public void logUserDeletion(Long userId) {
        logger.info("User with ID {} has been deleted", userId);
    }
}
