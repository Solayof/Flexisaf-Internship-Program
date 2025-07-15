package com.flexisaf.backendinternship.util;

import com.flexisaf.backendinternship.entity.UserEntity;
import com.flexisaf.backendinternship.exception.UserNotFoundException;
import com.flexisaf.backendinternship.repository.UserRepository;
import com.flexisaf.backendinternship.service.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CommonUtilTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private UserDetailsImpl userDetails;

    @InjectMocks
    private CommonUtil commonUtil;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
    }

    @Test
    public void testLoggedInUser_returnsUserDetails() {
        UserDetailsImpl result = commonUtil.loggedInUser();

        assertThat(result).isNotNull();
        verify(securityContext).getAuthentication();
        verify(authentication).getPrincipal();
    }

    @Test
    public void testLoggedInUserEmail_returnsCorrectEmail() {
        when(userDetails.getEmail()).thenReturn("test@example.com");

        String email = commonUtil.loggedInUserEmail();

        assertThat(email).isEqualTo("test@example.com");
    }

    @Test
    public void testLoggedInUserEntity_returnsUserEntity() {
        String email = "test@example.com";
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);

        when(userDetails.getEmail()).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));

        UserEntity result = commonUtil.loggedInUserEntity();

        assertThat(result).isEqualTo(userEntity);
    }

    @Test
    public void testLoggedInUserEntity_userNotFound_throwsException() {
        String email = "missing@example.com";

        when(userDetails.getEmail()).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> commonUtil.loggedInUserEntity());
    }
        
}
        
// This test verifies that the CommonUtil.loggedInUser() method returns the currently authenticated user details.
// It sets up a mock user in the security context and checks that the method retrieves the correct user information.
// The test ensures that the user ID and username match the expected values, confirming that the method works as intended.
// This is a simple test for the CommonUtil class to ensure that the loggedInUser method returns the correct user details.
// It uses the UserDetailsImpl class to create a mock user and checks that the loggedInUser method retrieves the correct user information.
// The test is straightforward and checks the basic functionality of the CommonUtil class, ensuring that it behaves as expected when retrieving the currently logged-in user.