package se.amandaflorencia.projektuppgiftits.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.amandaflorencia.projektuppgiftits.User;
import se.amandaflorencia.projektuppgiftits.UserRepository;
import se.amandaflorencia.projektuppgiftits.dto.UserRegistrationDTO;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void registerUser(UserRegistrationDTO userRegistrationDTO) {
        User user = new User();
        user.setUsername(userRegistrationDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        user.setRole(userRegistrationDTO.getRole());
        user.setConsentGiven(userRegistrationDTO.isConsentGiven());

        userRepository.save(user);
    }
}
