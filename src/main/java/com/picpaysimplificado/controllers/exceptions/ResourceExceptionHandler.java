package com.picpaysimplificado.controllers.exceptions;

import com.picpaysimplificado.services.exceptions.IntegrityViolation;
import com.picpaysimplificado.services.exceptions.ObjectNotFound;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFound.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFound object, HttpServletRequest request) {
        final var error = new StandardError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), object.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(IntegrityViolation.class)
    public ResponseEntity<StandardError> integrityViolation(IntegrityViolation violation, HttpServletRequest request) {
        final var error = new StandardError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), violation.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
