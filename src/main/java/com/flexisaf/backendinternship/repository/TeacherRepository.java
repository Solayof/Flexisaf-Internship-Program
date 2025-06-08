package com.flexisaf.backendinternship.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flexisaf.backendinternship.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, UUID> {
    Optional<Teacher> findById(String id);
}
