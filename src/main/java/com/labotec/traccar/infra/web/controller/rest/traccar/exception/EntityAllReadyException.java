package com.labotec.traccar.infra.web.controller.rest.traccar.exception;

public class EntityAllReadyException extends RuntimeException {
    public EntityAllReadyException(String message) {
        super(message);
    }
}
