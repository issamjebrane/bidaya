package com.bidaya.bidaya.Auth;

import com.bidaya.bidaya.users.User;
import com.bidaya.bidaya.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final TokenBlacklistService tokenBlacklistService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        Optional<User> userExist = this.userRepository.findByEmail(request.getEmail());
        if(userExist.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email Already Exists");
        }
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        tokenBlacklistService.blacklist(token.replace("Bearer ", ""));
        return ResponseEntity.ok().build();
    }
}
