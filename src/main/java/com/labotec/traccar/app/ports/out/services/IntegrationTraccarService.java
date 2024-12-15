package com.labotec.traccar.app.ports.out.services;

import com.labotec.traccar.domain.web.traccar.DeviceRequestDTO;
import com.labotec.traccar.domain.web.traccar.LastedInformationVehicle;

public interface IntegrationTraccarService {
    String processPosition(LastedInformationVehicle informationVehicle);
    void processPositionDataStop(DeviceRequestDTO deviceRequestDTO);
}
