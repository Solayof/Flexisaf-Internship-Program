package com.flexisaf.backendinternship.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flexisaf.backendinternship.constant.ERole;
import com.flexisaf.backendinternship.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, UUID> {
    Optional<RoleEntity> findByName(ERole name);   
}