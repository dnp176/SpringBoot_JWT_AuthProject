package com.jwt.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jwt.dto.RegisterRequest;
import com.jwt.repository.JwtUserRepository;
import com.jwt.entity.JwtUserLearn;
@Service
public class RegistrationService {
	
	@Autowired
    private JwtUserRepository userRepository;

	@Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public String registerUser(RegisterRequest registerRequest) {
    	
        // Check if the email already exists
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return "Error: Email is already in use!";
        }
        System.out.println(registerRequest.getLname());
        // Create the user object
        JwtUserLearn user = new JwtUserLearn();
        user.setFname(registerRequest.getFname());
        user.setLname(registerRequest.getLname());
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setRole(registerRequest.getRole()); 
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));  // Hash the password

        // Save the user to the database
        userRepository.save(user);
        
        return "User registered successfully!";
    }
}
