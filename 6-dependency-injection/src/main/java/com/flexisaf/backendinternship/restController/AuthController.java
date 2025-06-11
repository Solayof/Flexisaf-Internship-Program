package com.flexisaf.backendinternship.restController;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flexisaf.backendinternship.constant.ERole;
import com.flexisaf.backendinternship.dto.JwtResponseDTO;
import com.flexisaf.backendinternship.dto.LoginDTO;
import com.flexisaf.backendinternship.dto.ResponseMessageDTO;
import com.flexisaf.backendinternship.dto.SignupDTO;
import com.flexisaf.backendinternship.entity.RoleEntity;
import com.flexisaf.backendinternship.entity.UserEntity;
import com.flexisaf.backendinternship.repository.RoleRepository;
import com.flexisaf.backendinternship.repository.UserRepository;
import com.flexisaf.backendinternship.service.JwtServiceImpl;
import com.flexisaf.backendinternship.service.UserDetailsImpl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
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
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginDTO entity, HttpServletResponse httpResponse) {
       Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(entity.getEmail(), entity.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());


        Cookie cookie = new Cookie("jwt", jwt);
        cookie.setHttpOnly(true); 
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);

        httpResponse.addCookie(cookie);

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

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@Valid @RequestBody SignupDTO entity, HttpServletResponse httpResponse) {
       if (userRepository.existsByEmail(entity.getEmail())) {
        ResponseMessageDTO response = new ResponseMessageDTO();
       response.setMessage("Email already exist");
        return ResponseEntity
        .badRequest()
        .body(response);
       }

       UserEntity user = new UserEntity();
       user.setFirstName(entity.getFirstName());
       user.setMiddleName(entity.getMiddleName());
       user.setLastName(entity.getLastName());
       user.setCreatedAt(entity.getCreatedAt());
       user.setEmail(entity.getEmail());
       user.setDob(entity.getDob());
       user.setGender(entity.getGender());
       user.setPassword(encoder.encode(entity.getPassword()));
       Set<String> entityRoles = entity.getRoles();
       Set<RoleEntity> roles = new HashSet<>();

       if (entityRoles == null) {
        RoleEntity userRole = roleRepository.findByName(ERole.ROLE_USER)
        .orElseThrow(() -> new RuntimeException("Role not found"));
        roles.add(userRole);
       } else {
        entityRoles.forEach(role -> {
            switch (role) {
                case "ROLE_ADMIN":
                    RoleEntity adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Role not found"));
                    roles.add(adminRole);
                    break;
                case "ROLE_USER":
                    RoleEntity userRole = roleRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Role not found"));
                    roles.add(userRole);
                    break;
                case "ROLE_MODERATOR":
                    RoleEntity mdRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                        .orElseThrow(() -> new RuntimeException("Role not found"));
                    roles.add(mdRole);
                    break;
                default:
                    RoleEntity usrRole = roleRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Role not found"));
                    roles.add(usrRole);
            }
        });
       }

       user.setRoles(roles);

       userRepository.save(user);
       Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(entity.getEmail(), entity.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateJwtToken(authentication);

        Cookie cookie = new Cookie("jwt", jwt);
        cookie.setHttpOnly(true); 
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);

        httpResponse.addCookie(cookie);

       ResponseMessageDTO response = new ResponseMessageDTO();
       response.setJwt(jwt);
       response.setId(user.getId());
       response.setMessage("success");
       ResponseEntity<ResponseMessageDTO> res = new ResponseEntity<ResponseMessageDTO>(response, HttpStatus.CREATED);
       return res;

    }
    
}
