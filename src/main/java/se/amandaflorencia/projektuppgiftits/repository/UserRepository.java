package se.amandaflorencia.projektuppgiftits.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.amandaflorencia.projektuppgiftits.model.AppUser;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
}
