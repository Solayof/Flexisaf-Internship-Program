package com.flexisaf.backendinternship.exception;

import java.util.UUID;

public class TeacherNotFoundException extends RuntimeException {
    public TeacherNotFoundException(UUID id) {
        super("Could not find teacher with id: " + id);
    }
}
