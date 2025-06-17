package se.amandaflorencia.projektuppgiftits.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.amandaflorencia.projektuppgiftits.model.AppUser;

import java.util.Optional;
/** UserRepository är till för att koppla användaren i databasen till koden
 * Den ärver från JpaRepository som kommer med flera metoder för att t.ex
 * hitta användare genom användarnamn, id, ändra på fält, spara och radera mm.
 * */
public interface UserRepository extends JpaRepository<AppUser, Long> {

    /** Denna metod hittar användare genom användarnamn
     * Den returnerar en användare om den finns, annars tom mha optional
     * */
    Optional<AppUser> findByUsername(String username);
}
