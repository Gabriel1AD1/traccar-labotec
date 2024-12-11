package com.labotec.traccar.infra.db.mysql.jpa.traccar.implementation;

import com.labotec.traccar.app.ports.input.repository.DeviceTraccarRepository;
import com.labotec.traccar.infra.db.mysql.jpa.traccar.entity.TcDevice;
import com.labotec.traccar.infra.db.mysql.jpa.traccar.repository.TcDeviceRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeviceTraccarRepositoryImpl implements DeviceTraccarRepository {
    private final TcDeviceRepository tcDeviceRepository;
    @Override
    public Long createDevice(String imei, String name) {
        TcDevice tcDevice = new TcDevice();
        tcDevice.setName(name);
        tcDevice.setUniqueid(imei);
        return Long.valueOf(tcDeviceRepository.save(tcDevice).getId());
    }
}
