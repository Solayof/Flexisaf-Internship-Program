package com.flexisaf.backendinternship.service;

import com.flexisaf.backendinternship.entity.Course;
import com.flexisaf.backendinternship.exception.CourseNotFoundException;
import com.flexisaf.backendinternship.repository.CourseRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceTests {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById_whenCourseExists_returnsCourse() {
        UUID courseId = UUID.randomUUID();
        Course course = new Course();
        course.setId(courseId);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        Course found = courseService.findById(courseId);

        assertNotNull(found);
        assertEquals(courseId, found.getId());
        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    void testFindById_whenCourseNotFound_throwsException() {
        UUID courseId = UUID.randomUUID();

        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        assertThrows(CourseNotFoundException.class, () -> {
            courseService.findById(courseId);
        });

        verify(courseRepository, times(1)).findById(courseId);
    }
}
