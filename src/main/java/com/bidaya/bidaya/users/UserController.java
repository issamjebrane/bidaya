package com.bidaya.bidaya.users;

import jakarta.persistence.NoResultException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    UserController(UserRepository userRepository){
        this.userRepository = userRepository;

    }

    @GetMapping("")
    public List<User> getUsers(){
        return this.userRepository.findAll();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id){
         this.userRepository.deleteById(id);
         return "deleted";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            User loggedInUser = this.userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
            if (loggedInUser != null) {
                return ResponseEntity.ok(loggedInUser);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
            }
        } catch (NoResultException e) {
            System.out.println("No user found");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user){
        User userExist = this.userRepository.findByEmail(user.getEmail());
        if(userExist != null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email Already Exists");
        }
        User registeredUser = this.userRepository.save(user);
        return ResponseEntity.ok(registeredUser);
    }

}
