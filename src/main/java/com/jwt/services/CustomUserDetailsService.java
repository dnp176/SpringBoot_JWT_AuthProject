package com.jwt.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.jwt.entity.JwtUserLearn;
import com.jwt.repository.JwtUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
    private JwtUserRepository jwtUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        JwtUserLearn user = jwtUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Mapping JwtUserLearn to Spring Security's UserDetails
        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole())  // Roles should be fetched from the entity
                .build();
    }
}
