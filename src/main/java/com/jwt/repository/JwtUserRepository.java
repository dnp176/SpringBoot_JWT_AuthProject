package com.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jwt.entity.JwtUserLearn;

import java.util.Optional;

@Repository
public interface JwtUserRepository extends JpaRepository<JwtUserLearn, Long> {
    Optional<JwtUserLearn> findByEmail(String email);
    boolean existsByEmail(String email);
}
