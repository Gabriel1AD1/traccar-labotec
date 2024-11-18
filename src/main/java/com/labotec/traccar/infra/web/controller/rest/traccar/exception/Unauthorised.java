package com.labotec.traccar.infra.web.controller.rest.traccar.exception;

public class Unauthorised extends RuntimeException {
    public Unauthorised(String message) {
        super(message);
    }
}
