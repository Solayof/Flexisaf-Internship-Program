package com.flexisaf.backendinternship.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignuoDTO {
    @NotNull
    @Size(max = 25)
    private String firstName;
    @Size(max = 25)
    private String middleName;
    @NotNull
    @Size(max = 25)
    private String lastName;
    @NotNull
    @Size(max = 50)
    private String email;
    @Size(max = 25)
    private LocalDate dob;
    private String gender;
    @NotNull
    private String password;
    @NotNull
    private LocalDateTime createdAt;
    
}
