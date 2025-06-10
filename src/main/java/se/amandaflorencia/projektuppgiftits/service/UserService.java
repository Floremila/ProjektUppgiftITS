package se.amandaflorencia.projektuppgiftits.service;

import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import se.amandaflorencia.projektuppgiftits.AppUser;
import se.amandaflorencia.projektuppgiftits.UserRepository;
import se.amandaflorencia.projektuppgiftits.dto.UserRegistrationDTO;
import se.amandaflorencia.projektuppgiftits.exception.UserNotFoundException;
import se.amandaflorencia.projektuppgiftits.util.LoggingComponent;

import java.util.List;

@Service
@Validated
public class UserService {
    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private final LoggingComponent loggingComponent;


    public UserService(UserRepository userRepository, PasswordEncoder  passwordEncoder, LoggingComponent loggingComponent) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.loggingComponent = loggingComponent;
    }

    public List<AppUser> getAllUsers() {

        return userRepository.findAll();
    }

    public void registerUser(@Valid UserRegistrationDTO userRegistrationDTO) {
        AppUser appUser = new AppUser();
        appUser.setUsername(userRegistrationDTO.getUsername());
        appUser.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        appUser.setRole(userRegistrationDTO.getRole());
        appUser.setConsentGiven(userRegistrationDTO.isConsentGiven());

        userRepository.save(appUser);
        loggingComponent.logUserRegistration(appUser.getUsername());
    }



    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        userRepository.deleteById(id);
        loggingComponent.logUserDeletion(id);
    }



    public AppUser updateUser(Long id, AppUser updatedAppUser) {
        return userRepository.findById(id).map(appUser -> {
            appUser.setUsername(updatedAppUser.getUsername());
            appUser.setPassword(passwordEncoder.encode(updatedAppUser.getPassword()));
            appUser.setRole(updatedAppUser.getRole());
            appUser.setConsentGiven(updatedAppUser.isConsentGiven());
            return userRepository.save(appUser);
        }).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

}
