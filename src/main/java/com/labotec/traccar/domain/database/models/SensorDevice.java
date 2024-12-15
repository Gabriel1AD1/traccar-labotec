package com.labotec.traccar.domain.database.models;

import com.labotec.traccar.domain.enums.DataType;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class SensorDevice {
    private Long id;
    private Long deviceId;
    private String sensorName;
    private String stateCurrent; // 'estado_actual' en la BD
    private TypeSensor typeSensor;
    private DataType dataType;
    private Instant initStateCurrent; // 'inicio_estado_actual' en la BD
    private Long timeAcumulated; // 'tiempo_acumulado' en la BD

}
