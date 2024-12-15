package com.labotec.traccar.infra.db.mysql.jpa.labotec.projection;

import com.labotec.traccar.domain.database.models.optimized.TypeValidation;
import com.labotec.traccar.domain.enums.DataType;

public interface SensorValidationConfigProjection {
    Long getId();
    Long getUserId();
    String getNameSensor();
    String getOperator();
    String getValue();
    String getMessageAlert();
    DataType getDataType();
    TypeValidation getTypeValidation();
}
