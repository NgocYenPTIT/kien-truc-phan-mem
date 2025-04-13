package com.example.authservice.controller;

import com.example.authservice.DTOs.LoginRequest;
import com.example.authservice.DTOs.JwtResponse;

import com.example.authservice.model.User;
import com.example.authservice.security.JwtTokenProvider;
import com.example.authservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public AuthController(JwtTokenProvider jwtTokenProvider,UserService userService ) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> userOptional = this.userService.getUserByUsername((loginRequest.getUsername()));

        if(!userOptional.isPresent()) return ResponseEntity.status(401).body("Sai username hoặc password");

        User user = userOptional.get();

        if (user.getUsername().equals(loginRequest.getUsername()) && user.getPassword().equals(loginRequest.getPassword())) {
            String token = jwtTokenProvider.createToken(user.getId(), user.getUsername());
            return ResponseEntity.ok(new JwtResponse(token));
        }

        return ResponseEntity.status(401).body("Sai username hoặc password");
    }

    @GetMapping("/test-token")
    public ResponseEntity<?> testToken(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok("success");
    }

}