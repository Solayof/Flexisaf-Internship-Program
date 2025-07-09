package com.flexisaf.backendinternship.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flexisaf.backendinternship.entity.Course;
import com.flexisaf.backendinternship.entity.UserEntity;

public interface CourseRepository extends JpaRepository<Course, UUID>{
   List<Course> findAllByOwner(UserEntity owner); 
}
