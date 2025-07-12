package com.flexisaf.backendinternship.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flexisaf.backendinternship.entity.Course;

public interface CourseRepository extends JpaRepository<Course, UUID>{
    
}
