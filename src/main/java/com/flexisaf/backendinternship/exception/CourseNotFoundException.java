package com.flexisaf.backendinternship.exception;

import java.util.UUID;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(UUID id) {
        super("Could not find  " + id);
    }
}
