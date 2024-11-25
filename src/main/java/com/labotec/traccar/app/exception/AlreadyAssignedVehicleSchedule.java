package com.labotec.traccar.app.exception;

public class AlreadyAssignedVehicleSchedule extends RuntimeException {
    public AlreadyAssignedVehicleSchedule(String message) {
        super(message);
    }
}
