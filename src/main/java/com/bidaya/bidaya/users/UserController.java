package com.bidaya.bidaya.users;

import ch.qos.logback.core.boolex.Matcher;
import com.bidaya.bidaya.Auth.AuthenticationRequest;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("")
    public List<User> getUsers(){
        return this.userRepository.findAll();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
         this.userRepository.deleteById(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest user) {
        try {
            Optional<User> loggedInUser = this.userRepository.findByEmail(user.getEmail());
            if (loggedInUser.isPresent()) {
                if(passwordEncoder.matches(user.getPassword(), loggedInUser.get().getPassword())){
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    user.getEmail(),
                                    user.getPassword()
                            )
                    );
                }
                    return ResponseEntity.ok("");
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
        Optional<User> userExist = this.userRepository.findByEmail(user.getEmail());
        if(userExist.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email Already Exists");
        }
        User registeredUser = this.userRepository.save(user);
        return ResponseEntity.ok(registeredUser);
    }

}
