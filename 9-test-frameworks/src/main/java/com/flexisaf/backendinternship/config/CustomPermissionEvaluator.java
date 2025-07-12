package com.flexisaf.backendinternship.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.flexisaf.backendinternship.entity.Course;
import com.flexisaf.backendinternship.repository.CourseRepository;
import com.flexisaf.backendinternship.service.UserDetailsImpl;
import com.flexisaf.backendinternship.util.CommonUtil;

import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    CommonUtil commonUtil;

    @Override
    public boolean hasPermission(Authentication auth, Serializable targetId, String targetType, Object permission) {
        if (auth == null || targetType == null || permission == null || targetId == null)
            return false;

        boolean isAdmin = auth.getAuthorities().stream()
            .anyMatch(granted -> granted.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) return true;


    switch (targetType) {
        case "Document":
            return checkDocumentPermission(auth, targetId, permission.toString());
        case "Course":
            return checkCoursePermission(auth, targetId, permission.toString());
        case "Profile":
            return checkProfilePermission(auth, targetId, permission.toString());
        default:
            return false;
    }


        
    }

    private boolean checkDocumentPermission(Authentication auth, Serializable targetId, String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkDocumentPermission'");
    }

    private boolean checkCoursePermission(Authentication auth, Serializable targetId, String permission) {
        Optional<Course> optionalCourse = courseRepository.findById((UUID) targetId);

        if (optionalCourse.isEmpty()) return false;
        
        Course course = optionalCourse.get();
        UserDetailsImpl currentUser = commonUtil.loggedInUser();
        boolean isInstructor = course.getUser().getEmail().equals(currentUser.getEmail());

        switch (permission.toLowerCase()) {
        case "read":
            return true; 
        case "write":
            return isInstructor;
        case "delete":
            return isInstructor;
        default:
            return false;
        }
    }

    private boolean checkProfilePermission(Authentication auth, Serializable targetId, String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkProfilePermission'");
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object o, Object o1) {
        return false; // not used
    }
}
