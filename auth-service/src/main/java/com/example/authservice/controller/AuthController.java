package com.example.authservice.controller;

import com.example.authservice.DTOs.LoginRequest;
import com.example.authservice.DTOs.JwtResponse;

import com.example.authservice.DTOs.UserDto;
import com.example.authservice.security.JwtTokenProvider;
import com.example.authservice.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;

    public AuthController(JwtTokenProvider jwtTokenProvider, AuthService authService ) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return  this.authService.login(loginRequest);
    }

    @GetMapping("/test-token")
    public ResponseEntity<?> testToken(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok("success");
    }

}