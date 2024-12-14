package com.labotec.traccar.domain.database.models.optimized;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.TypeSensor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OptimizedSensorDevice {
    private String sensorName;
    private TypeSensor typeSensor;
}
