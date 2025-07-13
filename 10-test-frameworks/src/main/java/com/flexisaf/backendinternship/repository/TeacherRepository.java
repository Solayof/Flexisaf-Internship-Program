package com.flexisaf.backendinternship.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flexisaf.backendinternship.entity.Teacher;


public interface TeacherRepository extends JpaRepository<Teacher, UUID> {
}
