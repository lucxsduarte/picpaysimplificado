package com.picpaysimplificado.services.exceptions;

public class IntegrityViolation extends RuntimeException {
    public IntegrityViolation(final String message) {
        super(message);
    }
}
