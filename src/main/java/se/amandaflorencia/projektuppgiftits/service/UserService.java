package se.amandaflorencia.projektuppgiftits.service;


import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import se.amandaflorencia.projektuppgiftits.model.AppUser;
import se.amandaflorencia.projektuppgiftits.repository.UserRepository;
import se.amandaflorencia.projektuppgiftits.dto.UserRegistrationDTO;
import se.amandaflorencia.projektuppgiftits.exception.UserNotFoundException;
import se.amandaflorencia.projektuppgiftits.util.LoggingComponent;

import java.util.List;

/** UserService är servicelagret för användaren, kopplingen mellan databasen genom UserRepository
 * och controllern.
 * */
@Service
@Validated
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LoggingComponent loggingComponent;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       LoggingComponent loggingComponent) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.loggingComponent = loggingComponent;
    }

    /** Metoden hämtar alla användare genom inbyggd metod i UserRepository */
    public List<AppUser> getAllUsers() {
        logger.info("Fetching all users");
        return userRepository.findAll();
    }

    /** Metod för att registrera en ny användare
     * @param dto validerade värden som ska mappas till en AppUser.
     * Användaren sparas sen via UserRepository.
     * */
    public void registerUser(@Valid UserRegistrationDTO dto) {
        logger.info("Starting registration for user: {}", dto.getUsername());
        try {
            AppUser appUser = new AppUser();
            appUser.setUsername(dto.getUsername());
            appUser.setPassword(passwordEncoder.encode(dto.getPassword()));
            appUser.setRole(dto.getRole());
            appUser.setConsentGiven(dto.isConsentGiven());

            userRepository.save(appUser);
            loggingComponent.logUserRegistration(appUser.getUsername());
            logger.debug("User '{}' registered successfully with ID {}", appUser.getUsername(), appUser.getId());
        } catch (Exception e) {
            logger.error("Error registering user '{}'", dto.getUsername(), e);
            throw e;
        }

    }

    public void deleteUserById(Long id) {
        logger.info("Starting deletion of user with ID: {}", id);
        try {
            if (!userRepository.existsById(id)) {
                throw new UserNotFoundException("User with id " + id + " not found");
            }

            userRepository.deleteById(id);
            loggingComponent.logUserDeletion(id);
            logger.debug("User with ID {} deleted successfully", id);
        } catch (Exception e) {
            logger.error("Error deleting user with ID {}", id, e);
            throw e;
        }
    }

    public AppUser updateUser(Long id, AppUser updatedAppUser) {
        logger.info("Starting update for user with ID: {}", id);
        try {
            return userRepository.findById(id).map(existingUser -> {
                existingUser.setUsername(updatedAppUser.getUsername());
                existingUser.setPassword(passwordEncoder.encode(updatedAppUser.getPassword()));
                existingUser.setRole(updatedAppUser.getRole());
                existingUser.setConsentGiven(updatedAppUser.isConsentGiven());

                AppUser saved = userRepository.save(existingUser);
                logger.debug("User with ID {} updated successfully", id);
                return saved;
            }).orElseThrow(() -> {
                logger.warn("Attempted to update non-existent user with ID {}", id);
                return new UserNotFoundException("User with id " + id + " not found");
            });
        } catch (Exception e) {
            logger.error("Error updating user with ID {}", id, e);
            throw e;
        }
    }
}
