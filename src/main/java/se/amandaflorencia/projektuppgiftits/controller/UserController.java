package se.amandaflorencia.projektuppgiftits.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import se.amandaflorencia.projektuppgiftits.model.AppUser;
import se.amandaflorencia.projektuppgiftits.service.UserService;

import java.util.List;

/** REST-kontroller som används för användarhantering
 * Har olika endpoints för olika operationer - hämta, radera och uppdatera användare
 * Använder UserService för att få tillgång till affärslogik och databasen.
 * */

@Tag(
        name = "User Management",
        description = "Endpoints for viewing, updating and deleting users"
)
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /** Metod som hämtar alla användare, får endast användas av en användare med admin-roll
     * @return ResponseEntity statuskod, och om användare finns - lista med användare
     * */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<AppUser>> getAllUsers() {
        List<AppUser> appUsers = userService.getAllUsers();
        if (appUsers.isEmpty()){
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.ok(appUsers);
        }
    }

    /**
     * Deletes a user by ID. Only accessible by users with the ADMIN role.
     *
     * @param id the ID of the user to delete.
     * @return a response with no content if deletion is successful.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Updates the user with the given ID using the new data from the request body.
     * Only accessible by users with the ADMIN role.
     *
     * @param id the ID of the user to update.
     * @param updatedAppUser the new user info.
     * @return the updated user.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<AppUser> updateUser(@PathVariable Long id, @RequestBody AppUser updatedAppUser) {
        AppUser appUser = userService.updateUser(id, updatedAppUser);
        return ResponseEntity.ok(appUser);
    }


}
