package com.labotec.traccar.infra.db.mysql.jpa.labotec.projection;

import com.labotec.traccar.domain.database.models.TypeSensor;
import com.labotec.traccar.domain.enums.DataType;

/**
 * Projection for {@link com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.ExpectedSensorsEntity}
 */
public interface ResponseExpectedSensorsProjection {
    Long getId();

    String getDescriptionSensor();

    String getNameSensor();

    TypeSensor getTypeSensor();

    DataType getDataType();
}