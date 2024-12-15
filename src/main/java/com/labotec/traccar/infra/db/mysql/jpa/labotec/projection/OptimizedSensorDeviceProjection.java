package com.labotec.traccar.infra.db.mysql.jpa.labotec.projection;

import com.labotec.traccar.domain.database.models.TypeSensor;

public interface OptimizedSensorDeviceProjection {
    String getSensorName();
    TypeSensor getTypeSensor();
}
