package se.amandaflorencia.projektuppgiftits.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.amandaflorencia.projektuppgiftits.User;
import se.amandaflorencia.projektuppgiftits.UserService;

import java.util.List;

//Denna controller sköter åtgärder som kräver behörighet

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()){
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.ok(users);
        }
    }


}
