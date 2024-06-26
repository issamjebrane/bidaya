package com.bidaya.bidaya.users;

import ch.qos.logback.core.boolex.Matcher;
import com.bidaya.bidaya.Auth.AuthenticationRequest;
import com.bidaya.bidaya.dto.UserDto;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/all-users")
    public List<UserDto> getUsers(){
        return this.userRepository.findAll().stream()
                .map(user -> {
                    UserDto userDto = new UserDto();
                    userDto.setId(user.getId());
                    userDto.setFirstName(user.getFirstName());
                    userDto.setLastName(user.getLastName());
                    userDto.setEmail(user.getEmail());
                    userDto.setRole(user.getRole());
                    return userDto;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email){
        User user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("User not found")
        );
        return ResponseEntity.ok().body(UserDto.builder()
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .role(user.getRole())
                        .id(user.getId())
                .build());
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
