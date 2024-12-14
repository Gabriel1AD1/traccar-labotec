package com.labotec.traccar.infra.db.mysql.jpa.labotec.projection;

import com.labotec.traccar.domain.enums.DataType;

public interface ResponseSensorValidatorConfigProjection {
    String getNameSensor();  // Nombre del sensor
    String getOperator();    // Operador para comparar el valor
    String getValue();       // Valor que se compara
    String getMessageAlert(); // Mensaje de alerta
    DataType getDataType();  // Tipo de dato (INT, DOUBLE, TEXT)
}
