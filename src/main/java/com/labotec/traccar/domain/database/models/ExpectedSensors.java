package com.labotec.traccar.domain.database.models;

import com.labotec.traccar.domain.enums.DataType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExpectedSensors {
    private Long id;
    private Long userId;
    private Long companyId;
    private Long deviceId;
    private String nameSensor;
    private String descriptionSensor;
    private TypeSensor typeSensor;
    private DataType dataType;
}
