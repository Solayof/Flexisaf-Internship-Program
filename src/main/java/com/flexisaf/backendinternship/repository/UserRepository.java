package com.flexisaf.backendinternship.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flexisaf.backendinternship.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findById(String id);
    Optional<User> findByEmail(String email);
    Boolean existByEmail(String email);
}
