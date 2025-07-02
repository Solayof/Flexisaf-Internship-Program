package com.flexisaf.backendinternship.service;

import com.flexisaf.backendinternship.entity.UserEntity;
import com.flexisaf.backendinternship.exception.UserNotFoundException;
import com.flexisaf.backendinternship.repository.UserRepository;
import com.github.javafaker.Faker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private AutoCloseable closeable;

    private Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername_UserExists() {
        UserEntity user = new UserEntity();
        user.setEmail(faker.internet().emailAddress());
        user.setPassword("encodedpass");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername(user.getEmail());

        assertEquals(user.getEmail(), userDetails.getUsername());
        verify(userRepository, times(1)).findByEmail(user.getEmail());
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        when(userRepository.findByEmail("missing@example.com")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.loadUserByUsername("missing@example.com");
        });

        verify(userRepository, times(1)).findByEmail("missing@example.com");
    }

    @Test
    void testAddUser_EncodesPasswordAndSaves() {
        UserEntity user = new UserEntity();
        user.setEmail(faker.internet().emailAddress());
        user.setPassword("rawpassword");

        when(passwordEncoder.encode("rawpassword")).thenReturn("encodedPassword");

        String result = userService.addUser(user);

        assertEquals("UserEntity added Sucessfully", result);
        assertEquals("encodedPassword", user.getPassword());
        verify(userRepository, times(1)).save(user);
    }
}
