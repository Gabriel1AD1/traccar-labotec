package com.labotec.traccar.infra.db.mysql.jpa.labotec.projection;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.labotec.traccar.domain.database.models.optimized.TypeValidation;
import com.labotec.traccar.domain.enums.DataType;
import jakarta.validation.constraints.NotNull;

public interface SensorValidationConfigProjection {
    String getNameSensor();
    String getOperator();
    String getValue();
    String getMessageAlert();
    DataType getDataType();
    TypeValidation getTypeValidation();
}
