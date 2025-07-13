package com.flexisaf.backendinternship.restController;

import com.flexisaf.backendinternship.entity.Course;
import com.flexisaf.backendinternship.entity.UserEntity;
import com.flexisaf.backendinternship.service.CourseService;
import com.flexisaf.backendinternship.service.UserServiceImpl;
import com.flexisaf.backendinternship.util.CommonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @Mock
    private CourseModelAssembler assembler;

    @Mock
    private CommonUtil commonUtil;

    @Mock
    private UserServiceImpl userServiceImpl;

    @InjectMocks
    private CourseController controller;

    private Course course;
    private UUID courseId;
    private UserEntity user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        courseId = UUID.randomUUID();
        course = new Course();
        course.setId(courseId);
        course.setName("Java 101");
        course.setContent("Intro to Java");
        user = new UserEntity();
        user.setId(UUID.randomUUID());
    }

    @Test
    public void testCreateCourse_success() {
        when(courseService.createCourse(any())).thenReturn(course);
        when(assembler.toModel(course)).thenReturn(EntityModel.of(course));

        EntityModel<Course> result = controller.createCourse(course);
        assertNotNull(result);
        assertNotNull(result.getContent());
        assertEquals(course.getName(), Objects.requireNonNull(result.getContent()).getName());
    }

    @Test
    public void testGetAllCourses_success() {
        when(courseService.getAllCourses()).thenReturn(List.of(course));
        when(assembler.toModel(course)).thenReturn(EntityModel.of(course));

        CollectionModel<EntityModel<Course>> result = controller.courses();
        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    public void testGetCourseById_success() {
        when(courseService.findById(courseId)).thenReturn(course);
        when(assembler.toModel(course)).thenReturn(EntityModel.of(course));

        EntityModel<Course> result = controller.getOne(courseId);
        assertNotNull(result.getContent());
        Course resultCourse = result.getContent();
        assertNotNull(resultCourse);
        assertEquals(course.getContent(), resultCourse.getContent());
    }

    @Test
    public void testUpdateCourse_success() {
        Course updated = new Course();
        updated.setContent("Updated Content");

        when(courseService.findById(courseId)).thenReturn(course);
        when(courseService.updateCourse(any())).thenReturn(course);
        when(assembler.toModel(any())).thenReturn(EntityModel.of(course));

        EntityModel<Course> result = controller.updateOne(updated, courseId);
        assertNotNull(result);
        verify(courseService).updateCourse(any(Course.class));
    }

    @Test
    public void testGetMyCourses_success() {
        when(courseService.getMyCourses()).thenReturn(List.of(course));
        when(assembler.toModel(course)).thenReturn(EntityModel.of(course));

        CollectionModel<EntityModel<Course>> result = controller.getMyCourses();
        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    public void testGetTutorCourses_success() {
        when(userServiceImpl.getUserById(user.getId())).thenReturn(user);
        when(courseService.getCoursesByOwner(user)).thenReturn(List.of(course));
        when(assembler.toModel(course)).thenReturn(EntityModel.of(course));

        CollectionModel<EntityModel<Course>> result = controller.getTutorCourses(user.getId());
        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    public void testDeleteCourse_success() {
        when(courseService.deleteCourse(any(UUID.class))).thenReturn(true); 
        when(assembler.toModel(any())).thenReturn(EntityModel.of(course));


        ResponseEntity<?> response = controller.getMethodName(courseId);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertNotNull(response.getBody());
        Map<?, ?> responseBody = (Map<?, ?>) response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody.containsKey("message"));
        assertEquals(true, responseBody.get("message"));
    }

    @Test
    public void testUpdateCourse_whenCourseNotFound() {
        when(courseService.findById(courseId)).thenThrow(new RuntimeException("Not found"));

        assertThrows(RuntimeException.class, () -> {
            controller.updateOne(new Course(), courseId);
        });
    }

    @Test
    public void testGetOne_whenCourseNotFound() {
        when(courseService.findById(courseId)).thenThrow(new RuntimeException("Not found"));

        assertThrows(RuntimeException.class, () -> {
            controller.getOne(courseId);
        });
    }
}

