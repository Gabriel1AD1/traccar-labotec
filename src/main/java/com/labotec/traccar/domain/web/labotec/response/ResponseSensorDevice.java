package com.labotec.traccar.domain.web.labotec.response;

import com.labotec.traccar.domain.database.models.TypeSensor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ResponseSensorDevice {
    private String sensorName;
    private String stateCurrent; // 'estado_actual' en la BD
    private TypeSensor typeSensor;
    private Instant initStateCurrent; // 'inicio_estado_actual' en la BD
    private Long timeAcumulated; // 'tiempo_acumulado' en la BD
}
