package com.labotec.traccar.app.ports.out;

import com.labotec.traccar.domain.web.dto.traccar.DeviceRequestDTO;
import com.labotec.traccar.domain.web.dto.traccar.LastedInformationVehicle;

public interface IntegrationTraccarService {
    String processPosition(LastedInformationVehicle informationVehicle);
    void processPositionDataStop(DeviceRequestDTO deviceRequestDTO);
}
