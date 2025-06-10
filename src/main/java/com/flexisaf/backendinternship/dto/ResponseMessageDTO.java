package com.flexisaf.backendinternship.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseMessageDTO {
    private UUID id;
    private String message;

    public ResponseMessageDTO(UUID id, String message) {
        this.id = id;
        this.message = message;
    }
}
