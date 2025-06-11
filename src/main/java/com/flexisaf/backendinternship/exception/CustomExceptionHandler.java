package com.flexisaf.backendinternship.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
}
