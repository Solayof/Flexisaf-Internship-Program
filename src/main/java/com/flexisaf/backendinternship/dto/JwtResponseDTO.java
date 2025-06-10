package com.flexisaf.backendinternship.dto;

import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtResponseDTO {
    private String token;
    private String type = "Bearer";
    private UUID id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private List<String> roles;

    public JwtResponseDTO(
        String token,
        UUID id,
        String firstName,
        String middleName,
        String lastName,
        String email,
        List<String> roles
    ) {
        this.token = token;
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.roles = roles;
    }
}
