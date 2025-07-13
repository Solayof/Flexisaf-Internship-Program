package com.flexisaf.backendinternship.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.flexisaf.backendinternship.entity.Course;
import com.flexisaf.backendinternship.entity.UserEntity;
import com.flexisaf.backendinternship.exception.CourseNotFoundException;
import com.flexisaf.backendinternship.exception.UserNotFoundException;
import com.flexisaf.backendinternship.repository.CourseRepository;
import com.flexisaf.backendinternship.repository.UserRepository;
import com.flexisaf.backendinternship.util.CommonUtil;

@Service
public class CourseService {
   @Autowired
   CourseRepository courseRepository;
   @Autowired
   CommonUtil commonUtil;
   @Autowired
   UserRepository userRepository;

   @PreAuthorize("hasPermission(#id, 'Course', 'read')")
   public Course findById(UUID id) throws CourseNotFoundException {
    return courseRepository.findById(id)
    .orElseThrow(() -> new CourseNotFoundException(id));
   }

   public Course createCourse(Course course) throws UserNotFoundException {
       if (course.getContent() == null || course.getContent().isEmpty()) {
           course.setContent("No content provided");
       }
       UserEntity user = userRepository.findById(commonUtil.loggedInUserEntity().getId())
           .orElseThrow(() -> new UserNotFoundException("User not found"));
         course.setOwner(user);
        user.getCourses().add(course);
       return courseRepository.save(course);
   }
   @PreAuthorize("hasPermission(#course.id, 'Course', 'write')")
   public Course updateCourse(Course course) {
       return courseRepository.save(course);
   }
   @PreAuthorize("hasPermission(#id, 'Course', 'write')")
   public Boolean deleteCourse(UUID id) {
       Course course = findById(id);
       courseRepository.delete(course);
       return true;
   }

   public boolean existsById(UUID id) {
       return courseRepository.existsById(id);
   }

   public List<Course> getAllCourses() {
       return courseRepository.findAll();  
   }

   public List<Course> getCoursesByOwner(UserEntity owner) {
       return courseRepository.findAllByOwner(owner);
   }

   public List<Course> getMyCourses(){
    return commonUtil.loggedInUserEntity().getCourses()
        .stream()
        .map(course -> course)
        .toList();
   }
}

