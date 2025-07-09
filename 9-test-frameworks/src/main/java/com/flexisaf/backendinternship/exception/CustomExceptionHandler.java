package com.flexisaf.backendinternship.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.flexisaf.backendinternship.dto.ErrorDTO;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorDTO>> handleFieldException(MethodArgumentNotValidException ex) {
        ErrorDTO errorlDto = null;
        List<ErrorDTO> errorDTOs =new ArrayList<>();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        for (FieldError fe: fieldErrors) {
            errorlDto = new ErrorDTO();
            errorlDto.setCode(fe.getField());
            errorlDto.setMessage(fe.getDefaultMessage());
            errorDTOs.add(errorlDto);
        }

        return new ResponseEntity<List<ErrorDTO>>(errorDTOs, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        Map<String, String> error = new HashMap<>();

        error.put("error", "Invalid input for parameter: " + ex.getName());
        Class<?> requiredType = ex.getRequiredType();
        error.put("expectedType", requiredType != null ? requiredType.getSimpleName() : "unknown");

        error.put("message", ex.getMessage());

        return new  ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
