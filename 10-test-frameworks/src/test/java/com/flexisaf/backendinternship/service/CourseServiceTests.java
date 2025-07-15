package com.flexisaf.backendinternship.service;

import com.flexisaf.backendinternship.entity.Course;
import com.flexisaf.backendinternship.entity.UserEntity;
import com.flexisaf.backendinternship.exception.CourseNotFoundException;
import com.flexisaf.backendinternship.exception.UserNotFoundException;
import com.flexisaf.backendinternship.repository.CourseRepository;
import com.flexisaf.backendinternship.repository.UserRepository;
import com.flexisaf.backendinternship.util.CommonUtil;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceTests {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommonUtil commonUtil;

    @InjectMocks
    private CourseService courseService;

    private AutoCloseable closeable;

    private UUID courseId;
    private Course course;
    private UserEntity user;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        courseId = UUID.randomUUID();

        user = new UserEntity();
        user.setId(UUID.randomUUID());
        user.setCourses(new HashSet<>());

        course = new Course();
        course.setId(courseId);
        course.setContent("Intro to Testing");
        course.setContent("Course content");
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testFindById_whenCourseExists_returnsCourse() {
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        Course result = courseService.findById(courseId);

        assertNotNull(result);
        assertEquals(courseId, result.getId());
        verify(courseRepository).findById(courseId);
    }

    @Test
    void testFindById_whenCourseNotFound_throwsException() {
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        assertThrows(CourseNotFoundException.class, () -> courseService.findById(courseId));
    }

    @Test
    void testCreateCourse_success() {
        when(commonUtil.loggedInUserEntity()).thenReturn(user);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        Course result = courseService.createCourse(course);

        assertNotNull(result);
        assertEquals(user, result.getOwner());
        assertTrue(user.getCourses().contains(course));
    }

    @Test
    void testCreateCourse_setsDefaultContentIfEmpty() {
        Course newCourse = new Course();

        when(commonUtil.loggedInUserEntity()).thenReturn(user);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(courseRepository.save(any(Course.class))).thenReturn(newCourse);

        Course result = courseService.createCourse(newCourse);

        assertEquals("No content provided", result.getContent());
    }

    @Test
    void testCreateCourse_whenUserNotFound_throwsException() {
        when(commonUtil.loggedInUserEntity()).thenReturn(user);
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> courseService.createCourse(course));
    }

    @Test
    void testUpdateCourse_success() {
        when(courseRepository.save(course)).thenReturn(course);

        Course result = courseService.updateCourse(course);

        assertEquals(course, result);
        verify(courseRepository).save(course);
    }

    @Test
    void testDeleteCourse_success() {
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        boolean result = courseService.deleteCourse(courseId);

        assertTrue(result);
        verify(courseRepository).delete(course);
    }

    @Test
    void testExistsById_returnsCorrectBoolean() {
        when(courseRepository.existsById(courseId)).thenReturn(true);

        boolean exists = courseService.existsById(courseId);

        assertTrue(exists);
    }

    @Test
    void testGetAllCourses_returnsList() {
        List<Course> mockCourses = List.of(course);
        when(courseRepository.findAll()).thenReturn(mockCourses);

        List<Course> result = courseService.getAllCourses();

        assertEquals(1, result.size());
    }

    @Test
    void testGetCoursesByOwner_returnsOwnerCourses() {
        List<Course> ownerCourses = List.of(course);
        when(courseRepository.findAllByOwner(user)).thenReturn(ownerCourses);

        List<Course> result = courseService.getCoursesByOwner(user);

        assertEquals(1, result.size());
    }

    @Test
    void testGetMyCourses_returnsUserCourses() {
        Course myCourse = new Course();
        myCourse.setContent("My Course");
        user.setCourses(Set.of(myCourse));

        when(commonUtil.loggedInUserEntity()).thenReturn(user);

        List<Course> result = courseService.getMyCourses();

        assertEquals(1, result.size());
        assertEquals("My Course", result.get(0).getContent());
    }
}
