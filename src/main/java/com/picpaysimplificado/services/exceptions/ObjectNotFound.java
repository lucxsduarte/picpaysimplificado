package com.picpaysimplificado.services.exceptions;

public class ObjectNotFound extends RuntimeException{
    public ObjectNotFound(final String message) {
        super(message);
    }
}
