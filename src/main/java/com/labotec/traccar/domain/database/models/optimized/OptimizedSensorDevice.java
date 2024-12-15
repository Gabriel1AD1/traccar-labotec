package com.labotec.traccar.domain.database.models.optimized;

import com.labotec.traccar.domain.database.models.TypeSensor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OptimizedSensorDevice {
    private String sensorName;
    private TypeSensor typeSensor;
}
