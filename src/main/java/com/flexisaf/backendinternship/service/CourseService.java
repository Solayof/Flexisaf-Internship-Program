package com.flexisaf.backendinternship.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.flexisaf.backendinternship.entity.Course;
import com.flexisaf.backendinternship.entity.UserEntity;
import com.flexisaf.backendinternship.exception.CourseNotFoundException;
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
   public Course findById(UUID id) {
    return courseRepository.findById(id)
    .orElseThrow(() -> new CourseNotFoundException(id));
   }
//    @PreAuthorize("hasPermission(#course.id, 'Course', 'write')")
   public Course createCourse(Course course) {
       if (course.getContent() == null || course.getContent().isEmpty()) {
           course.setContent("No content provided");
       }
       UserEntity user = userRepository.findById(commonUtil.loggedInUserEntity().getId())
           .orElseThrow(() -> new RuntimeException("User not found"));
         course.setOwner(user);
        user.getCourses().add(course);
       return courseRepository.save(course);
   }
   @PreAuthorize("hasPermission(#course.id, 'Course', 'write')")
   public Course updateCourse(Course course) {
       return createCourse(course);
   }
   @PreAuthorize("hasPermission(#id, 'Course', 'write')")
   public void deleteCourse(UUID id) {
       Course course = findById(id);
       courseRepository.delete(course);
   }
//    @PreAuthorize("hasPermission(#id, 'Course', 'read')")
   public boolean existsById(UUID id) {
       return courseRepository.existsById(id);
   }
//    @PreAuthorize("hasPermission(#course.id, 'Course', 'read')")
   public List<Course> getAllCourses() {
    //    return courseRepository.findAll()
    //    .stream()
    //    .filter(course -> course.getOwner().getEmail().equals(commonUtil.loggedInUserEmail()))
    //    .toList();
        return commonUtil.loggedInUserEntity().getCourses()
        .stream()
        .map(course -> course)
        .toList();
   }

//    @PreAuthorize("hasPermission(#email, 'Course', 'read')")
   public List<Course> getCoursesByEmail(UserEntity owner) {
       return courseRepository.findAllByOwner(owner);
   }
}

