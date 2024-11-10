package com.jwt.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.dto.LoginRequest;
import com.jwt.dto.RegisterRequest;
import com.jwt.services.CustomUserDetailsService;
import com.jwt.services.RegistrationService;
import com.jwt.util.JwtUtil;

import io.jsonwebtoken.Claims;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;
    
    @Autowired
    private RegistrationService registrationService;
    
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
    	System.out.println("1111");
        String response = registrationService.registerUser(registerRequest);
        if (response.equals("User registered successfully!")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/login")
    public String createToken(@RequestBody LoginRequest loginRequest) {
    	System.out.println("2222");
        try {
            // Authenticate user with email and password
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            // Fetch user details after successful authentication
            final var userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
            String username = userDetails.getUsername();  // Username is retrieved here
            System.out.println(username);

            // Generate token with email, password, and username
            return jwtUtil.generateToken( loginRequest.getEmail(), loginRequest.getPassword());

        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid credentials");
        }
    }
    
    @PostMapping("/decode")
    public ResponseEntity<Map<String, Object>> decodeToken(@RequestBody String token) {
    	System.out.println("qqqqq");
        try {
            Claims claims = jwtUtil.extractAllClaims(token);
            return ResponseEntity.ok(claims);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid token22"));
        }
    }
}
