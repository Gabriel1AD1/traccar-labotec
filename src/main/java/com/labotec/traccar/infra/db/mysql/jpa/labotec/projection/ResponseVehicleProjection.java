package com.labotec.traccar.infra.db.mysql.jpa.labotec.projection;

import com.labotec.traccar.domain.enums.STATE;

public interface ResponseVehicleProjection {
    Long getTraccarDeviceId();
    String getLicensePlate();
    String getTypeVehicleName();
    STATE getStatus();
    String getRegisterNumber();
    String getBrand();
    String getModel();
}
