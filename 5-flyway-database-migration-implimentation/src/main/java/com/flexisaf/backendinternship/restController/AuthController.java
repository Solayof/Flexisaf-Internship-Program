package com.flexisaf.backendinternship.restController;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flexisaf.backendinternship.dto.JwtResponseDTO;
import com.flexisaf.backendinternship.dto.LoginDTO;
import com.flexisaf.backendinternship.repository.RoleRepository;
import com.flexisaf.backendinternship.repository.UserRepository;
import com.flexisaf.backendinternship.service.JwtServiceImpl;
import com.flexisaf.backendinternship.service.UserDetailsImpl;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/noauth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtServiceImpl jwtService;


    @PostMapping("/signin")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO entity) {
       Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(entity.getEmail(), entity.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponseDTO(
            jwt,
            userDetails.getId(),
            userDetails.getFirstName(),
            userDetails.getMiddleName(),
            userDetails.getLastName(),
            userDetails.getEmail(),
            roles   
        ));
    }
}
