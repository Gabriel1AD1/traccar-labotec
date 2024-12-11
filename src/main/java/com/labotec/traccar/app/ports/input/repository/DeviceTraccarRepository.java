package com.labotec.traccar.app.ports.input.repository;

public interface DeviceTraccarRepository {
    Long createDevice(String imei, String name);
}
