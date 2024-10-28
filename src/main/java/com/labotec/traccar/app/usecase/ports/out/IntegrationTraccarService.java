package com.labotec.traccar.app.usecase.ports.out;

import com.labotec.traccar.domain.web.dto.LastedInformationVehicle;

public interface IntegrationTraccarService {
    String processPosition(LastedInformationVehicle informationVehicle);

}
