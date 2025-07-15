package com.flexisaf.backendinternship.service;

import com.flexisaf.backendinternship.exception.InvalidJwtTokenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtServiceImplTest {

    private JwtServiceImpl jwtService;

    private final String secret = "bXlzZWNyZXRrZXlmb3J0ZXN0aW5ncHVycG9zZXMerkygayYRYYGFVIGygfyigifriygY7UTRuiritfrtirietr="; 
    private final int expirationMs = 1000 * 60 * 60;

    @Mock
    private Authentication authentication;

    private UserDetailsImpl userDetails;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        jwtService = new JwtServiceImpl();

        Field jwtSecretField = JwtServiceImpl.class.getDeclaredField("jwtSecret");
        jwtSecretField.setAccessible(true);
        jwtSecretField.set(jwtService, secret);

        Field expirationField = JwtServiceImpl.class.getDeclaredField("jwtExpirationMs");
        expirationField.setAccessible(true);
        expirationField.set(jwtService, expirationMs);

        userDetails = UserDetailsImpl.build(new com.flexisaf.backendinternship.entity.UserEntity() {{
            setId(UUID.randomUUID());
            setEmail("test@example.com");
            setPassword("password");
            setRoles(new java.util.HashSet<>());
        }});
    }

    @Test
    void testGenerateTokenAndExtractUsername() {
        String token = jwtService.generateToken(userDetails.getUsername());

        String extractedUsername = jwtService.extractUsername(token);

        assertEquals(userDetails.getUsername(), extractedUsername);
    }

    @Test
    void testGenerateJwtTokenFromAuthentication() {
        when(authentication.getPrincipal()).thenReturn(userDetails);

        String token = jwtService.generateJwtToken(authentication);
        assertNotNull(token);

        String extracted = jwtService.extractUsername(token);
        assertEquals(userDetails.getEmail(), extracted);
    }

    @Test
    void testExtractExpirationNotExpired() {
        String token = jwtService.generateToken(userDetails.getUsername());
        Date exp = jwtService.extractExpiration(token);

        assertTrue(exp.after(new Date()));
    }

    @Test
    void testValidateJwtToken_validToken_returnsTrue() {
        String token = jwtService.generateToken(userDetails.getUsername());

        assertTrue(jwtService.validateJwtToken(token));
    }

    @Test
    void testValidateJwtToken_expiredToken_returnsFalse() throws Exception {
        Field expirationField = JwtServiceImpl.class.getDeclaredField("jwtExpirationMs");
        expirationField.setAccessible(true);
        expirationField.set(jwtService, -1000);
        String token = jwtService.generateToken(userDetails.getUsername());

        assertFalse(jwtService.validateJwtToken(token));
    }

    @Test
    void testValidateTokenWithCorrectUser() {
        String token = jwtService.generateToken(userDetails.getUsername());
        assertTrue(jwtService.validateToken(token, userDetails));
    }

    @Test
    void testValidateTokenWithIncorrectUser() {
        String token = jwtService.generateToken("wronguser@example.com");
        assertFalse(jwtService.validateToken(token, userDetails));
    }

    @Test
    void testExtractAllClaims_withInvalidToken_throwsException() {
        String badToken = "invalid.token.value";

        assertThrows(InvalidJwtTokenException.class, () -> jwtService.extractUsername(badToken));
    }
}
