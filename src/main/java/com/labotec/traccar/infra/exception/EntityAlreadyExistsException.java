package com.labotec.traccar.infra.exception;

public class EntityAlreadyExistsException extends RuntimeException {

    // Constructor que acepta solo un mensaje
    public EntityAlreadyExistsException(String message) {
        super(message);
    }

    // Constructor que acepta un mensaje y una causa
    public EntityAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
