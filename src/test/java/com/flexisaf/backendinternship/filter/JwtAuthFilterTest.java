package com.flexisaf.backendinternship.filter;

import com.flexisaf.backendinternship.service.JwtServiceImpl;
import com.flexisaf.backendinternship.service.UserServiceImpl;
import com.flexisaf.backendinternship.service.UserDetailsImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtAuthFilterTest {

    @InjectMocks
    private JwtAuthFilter jwtAuthFilter;

    @Mock
    private JwtServiceImpl jwtService;

    @Mock
    private UserServiceImpl userDetailsService;

    @Mock
    private FilterChain filterChain;

    private UserDetailsImpl userDetails;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        userDetails = UserDetailsImpl.build(new com.flexisaf.backendinternship.entity.UserEntity() {{
            setId(UUID.randomUUID());
            setEmail("test@example.com");
            setPassword("encoded");
            setRoles(new java.util.HashSet<>());
        }});
    }

    @Test
    void testValidJwt_setsAuthentication() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        String token = "valid.jwt.token";
        request.addHeader("Authorization", "Bearer " + token);

        when(jwtService.extractUsername(token)).thenReturn(userDetails.getUsername());
        when(userDetailsService.loadUserByUsername(userDetails.getUsername())).thenReturn(userDetails);
        when(jwtService.validateToken(token, userDetails)).thenReturn(true);

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(userDetails.getUsername(),
                SecurityContextHolder.getContext().getAuthentication().getName());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testInvalidToken_returnsUnauthorized() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        String token = "bad.jwt.token";
        request.addHeader("Authorization", "Bearer " + token);

        when(jwtService.extractUsername(token)).thenThrow(new com.flexisaf.backendinternship.exception.InvalidJwtTokenException("Invalid"));

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        assertEquals(401, response.getStatus());
        assertTrue(response.getContentAsString().contains("Unauthorized"));
    }

    @Test
    void testNoToken_doesNothingAndContinues() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}
