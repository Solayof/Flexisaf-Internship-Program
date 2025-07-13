package com.flexisaf.backendinternship.config;

import com.flexisaf.backendinternship.entity.Course;
import com.flexisaf.backendinternship.entity.UserEntity;
import com.flexisaf.backendinternship.repository.CourseRepository;
import com.flexisaf.backendinternship.service.UserDetailsImpl;
import com.flexisaf.backendinternship.util.CommonUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomPermissionEvaluatorTest {

    @InjectMocks
    private CustomPermissionEvaluator evaluator;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CommonUtil commonUtil;

    @Mock
    private Authentication auth;

    private UUID courseId;
    private Course course;
    private UserEntity owner;
    private UserDetailsImpl userDetails;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        courseId = UUID.randomUUID();
        owner = new UserEntity();
        owner.setEmail("owner@example.com");

        course = new Course();
        course.setId(courseId);
        course.setOwner(owner);

        userDetails = mock(UserDetailsImpl.class);
        when(userDetails.getEmail()).thenReturn("owner@example.com");
    }

    @Test
    void testHasPermission_AdminAccessGranted() {
        doReturn(
            List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
        ).when(auth).getAuthorities();

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        boolean result = evaluator.hasPermission(auth, courseId, "Course", "read");
        assertTrue(result);
    }

    @Test
    void testHasPermission_InvalidArgs_ReturnsFalse() {
        boolean result = evaluator.hasPermission(null, courseId, "Course", "read");
        assertFalse(result);
        result = evaluator.hasPermission(auth, null, "Course", "read");
        assertFalse(result);
        result = evaluator.hasPermission(auth, courseId, null, "read");
        assertFalse(result);
        result = evaluator.hasPermission(auth, courseId, "Course", null);
        assertFalse(result);
    }

    @Test
    void testHasPermission_ReadCourse_ReturnsTrue() {
        when(auth.getAuthorities()).thenReturn(List.of());
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(commonUtil.loggedInUser()).thenReturn(userDetails);

        boolean result = evaluator.hasPermission(auth, courseId, "Course", "read");
        assertTrue(result);
    }

    @Test
    void testHasPermission_WriteCourse_Owner_ReturnsTrue() {
        when(auth.getAuthorities()).thenReturn(List.of());
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(commonUtil.loggedInUser()).thenReturn(userDetails);

        boolean result = evaluator.hasPermission(auth, courseId, "Course", "write");
        assertTrue(result);
    }

    @Test
    void testHasPermission_WriteCourse_NotOwner_ReturnsFalse() {
        userDetails = mock(UserDetailsImpl.class);
        when(userDetails.getEmail()).thenReturn("not-owner@example.com");
        when(auth.getAuthorities()).thenReturn(List.of());
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(commonUtil.loggedInUser()).thenReturn(userDetails);

        boolean result = evaluator.hasPermission(auth, courseId, "Course", "write");
        assertFalse(result);
    }

    @Test
    void testHasPermission_CourseNotFound_ReturnsFalse() {
        when(auth.getAuthorities()).thenReturn(List.of());
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        boolean result = evaluator.hasPermission(auth, courseId, "Course", "read");
        assertFalse(result);
    }

    @Test
    void testHasPermission_UnknownTargetType_ReturnsFalse() {
        when(auth.getAuthorities()).thenReturn(List.of());

        boolean result = evaluator.hasPermission(auth, courseId, "UnknownType", "read");
        assertFalse(result);
    }

    @Test
    void testHasPermission_UnsupportedPermission_ReturnsFalse() {
        when(auth.getAuthorities()).thenReturn(List.of());
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(commonUtil.loggedInUser()).thenReturn(userDetails);

        boolean result = evaluator.hasPermission(auth, courseId, "Course", "fly");
        assertFalse(result);
    }
}
