package com.flexisaf.backendinternship.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.flexisaf.backendinternship.entity.UserEntity;
import com.flexisaf.backendinternship.exception.UserNotFoundException;
import com.flexisaf.backendinternship.repository.UserRepository;
import com.flexisaf.backendinternship.service.UserDetailsImpl;

@Component
public class CommonUtil {

    @Autowired
    private UserRepository userRepository;

    public UserDetailsImpl loggedInUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails;
    }

    public String loggedInUserEmail() {
        UserDetailsImpl userDetails = loggedInUser();
        return userDetails.getEmail();
    }
    public UserEntity loggedInUserEntity() {
        return userRepository.findByEmail(loggedInUserEmail())
                .orElseThrow(() -> new UserNotFoundException(loggedInUserEmail()));
    }
}

