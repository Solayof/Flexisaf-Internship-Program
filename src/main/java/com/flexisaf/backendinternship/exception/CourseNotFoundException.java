package com.flexisaf.backendinternship.exception;

import java.util.UUID;

class CourseNotFoundException extends RuntimeException {
    CourseNotFoundException(UUID id) {
        super("Could not find  " + id);
    }
}
