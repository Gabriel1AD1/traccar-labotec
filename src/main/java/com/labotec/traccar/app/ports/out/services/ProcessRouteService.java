package com.labotec.traccar.app.ports.out.services;

import com.labotec.traccar.domain.web.traccar.DeviceRequestDTO;

public interface ProcessRouteService {
    void validateRoute(DeviceRequestDTO deviceRequestDTO);
}
