package com.flexisaf.backendinternship.exception;

import java.util.UUID;

class TeacherNotFoundException extends RuntimeException {
    TeacherNotFoundException(UUID id) {
        super("Could not find teacher " + id);
    }
}
