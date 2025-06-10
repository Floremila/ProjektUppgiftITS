package se.amandaflorencia.projektuppgiftits.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import se.amandaflorencia.projektuppgiftits.model.AppUser;
import se.amandaflorencia.projektuppgiftits.service.UserService;

import java.util.List;



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

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<AppUser> updateUser(@PathVariable Long id, @RequestBody AppUser updatedAppUser) {
        AppUser appUser = userService.updateUser(id, updatedAppUser);
        return ResponseEntity.ok(appUser);
    }


}
