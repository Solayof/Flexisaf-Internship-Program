package com.flexisaf.backendinternship.util;

import com.flexisaf.backendinternship.service.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CommonUtilTest {

    private CommonUtil commonUtil;

    private UserDetailsImpl userDetails;

    @BeforeEach
    void setUp() {
        commonUtil = new CommonUtil();

        userDetails = UserDetailsImpl.build(new com.flexisaf.backendinternship.entity.UserEntity() {{
            setId(UUID.randomUUID());
            setEmail("test@example.com");
            setPassword("encoded-password");
            setRoles(new HashSet<>());
        }});

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    @Test
    void testLoggedInUser_returnsCorrectUser() {
        UserDetailsImpl result = commonUtil.loggedInUser();

        assertNotNull(result);
        assertEquals(userDetails.getUsername(), result.getUsername());
        assertEquals(userDetails.getId(), result.getId());
    }
}
// This test verifies that the CommonUtil.loggedInUser() method returns the currently authenticated user details.
// It sets up a mock user in the security context and checks that the method retrieves the correct user information.
// The test ensures that the user ID and username match the expected values, confirming that the method works as intended.
// This is a simple test for the CommonUtil class to ensure that the loggedInUser method returns the correct user details.
// It uses the UserDetailsImpl class to create a mock user and checks that the loggedInUser method retrieves the correct user information.
// The test is straightforward and checks the basic functionality of the CommonUtil class, ensuring that it behaves as expected when retrieving the currently logged-in user.