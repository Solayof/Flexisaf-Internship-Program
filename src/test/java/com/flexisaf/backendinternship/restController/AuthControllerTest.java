package com.flexisaf.backendinternship.restController;

import com.flexisaf.backendinternship.constant.ERole;
import com.flexisaf.backendinternship.constant.UserType;
import com.flexisaf.backendinternship.dto.LoginDTO;
import com.flexisaf.backendinternship.dto.SignupDTO;
import com.flexisaf.backendinternship.entity.RoleEntity;
import com.flexisaf.backendinternship.entity.UserEntity;
import com.flexisaf.backendinternship.entity.UserTypeEntity;
import com.flexisaf.backendinternship.repository.RoleRepository;
import com.flexisaf.backendinternship.repository.UserRepository;
import com.flexisaf.backendinternship.repository.UserTypeRepository;
import com.flexisaf.backendinternship.restController.AuthController;
import com.flexisaf.backendinternship.service.JwtServiceImpl;
import com.flexisaf.backendinternship.service.UserDetailsImpl;
import com.flexisaf.backendinternship.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.servlet.http.HttpServletResponse;

import java.text.Collator;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @Mock private AuthenticationManager authenticationManager;
    @Mock private UserRepository userRepository;
    @Mock private RoleRepository roleRepository;
    @Mock private UserTypeRepository userTypeRepository;
    @Mock private JwtServiceImpl jwtService;
    @Mock private UserServiceImpl userServiceImpl;
    @Mock private HttpServletResponse response;

    @InjectMocks private AuthController authController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginUser_success() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("test@example.com");
        loginDTO.setPassword("pass");

        Authentication auth = mock(Authentication.class);
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        when(auth.getPrincipal()).thenReturn(userDetails);
        when(authenticationManager.authenticate(any())).thenReturn(auth);
        when(jwtService.generateJwtToken(any())).thenReturn("jwt-token");

        Collection<? extends GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        when(userDetails.getAuthorities()).thenReturn(authorities);
        // when(userDetails.getAuthorities()).thenReturn(
        //     List.of("ROLE_ADMIN")
        //         .stream()
        //         .map(role -> new SimpleGrantedAuthority(role))
        //         .collect(Collectors.toList())
        // );
        // when(userDetails.getAuthorities()).thenReturn(List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        when(userDetails.getId()).thenReturn(UUID.randomUUID());
        when(userDetails.getFirstName()).thenReturn("John");
        when(userDetails.getMiddleName()).thenReturn("Doe");
        when(userDetails.getLastName()).thenReturn("Smith");
        when(userDetails.getEmail()).thenReturn("test@example.com");

        ResponseEntity<?> result = authController.loginUser(loginDTO, response);
        assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    void testCreateUser_success() {
        SignupDTO signupDTO = new SignupDTO();
        signupDTO.setEmail("newuser@example.com");
        signupDTO.setPassword("123456");
        signupDTO.setFirstName("Test");
        signupDTO.setDob(LocalDate.now());
        signupDTO.setUserType("TEACHER");
        signupDTO.setRoles(Set.of("ROLE_USER"));

        when(userRepository.existsByEmail(signupDTO.getEmail())).thenReturn(false);

        UserTypeEntity userType = new UserTypeEntity();
        userType.setName(UserType.TEACHER);
        when(userTypeRepository.findByName(UserType.TEACHER)).thenReturn(Optional.of(userType));

        when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.of(new RoleEntity(UUID.randomUUID(), ERole.ROLE_USER)));
        when(roleRepository.findByName(ERole.READ)).thenReturn(Optional.of(new RoleEntity(UUID.randomUUID(), ERole.READ)));
        when(roleRepository.findByName(ERole.WRITE)).thenReturn(Optional.of(new RoleEntity(UUID.randomUUID(), ERole.WRITE)));
        when(roleRepository.findByName(ERole.ROLE_MODERATOR)).thenReturn(Optional.of(new RoleEntity(UUID.randomUUID(), ERole.ROLE_MODERATOR)));

        Authentication auth = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(auth);
        when(jwtService.generateJwtToken(any())).thenReturn("jwt-token");

        ResponseEntity<?> response = authController.createUser(signupDTO, this.response);
        assertEquals(201, response.getStatusCodeValue());
    }
}
