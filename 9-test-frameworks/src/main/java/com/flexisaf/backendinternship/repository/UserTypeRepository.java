package com.flexisaf.backendinternship.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flexisaf.backendinternship.constant.UserType;
import com.flexisaf.backendinternship.entity.UserTypeEntity;

public interface UserTypeRepository extends JpaRepository<UserTypeEntity, UUID> {
    Optional<UserTypeEntity> findByName(UserType name);   
}