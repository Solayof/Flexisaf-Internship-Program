package com.flexisaf.backendinternship.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import com.flexisaf.backendinternship.entity.Course;
import com.flexisaf.backendinternship.exception.CourseNotFoundException;
import com.flexisaf.backendinternship.repository.CourseRepository;

public class CourseService {
   @Autowired
   CourseRepository courseRepository;

   @PreAuthorize("hasPermission(#id, 'Course', 'read')")
   public Course findById(UUID id) {
    return courseRepository.findById(id)
    .orElseThrow(() -> new CourseNotFoundException(id));
   }
}
