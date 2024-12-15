package com.labotec.traccar.infra.db.mysql.jpa.labotec.projection;

import com.labotec.traccar.domain.database.models.TypeSensor;

import java.time.Instant;

public interface ResponseSensorDeviceProjection {
    String getSensorName(); // nombre del sensor
    String getStateCurrent(); // estado actual
    TypeSensor getTypeSensor(); // tipo de sensor
    Instant getInitStateCurrent(); // inicio del estado actual
    Long getTimeAcumulated(); // tiempo acumulado
}
