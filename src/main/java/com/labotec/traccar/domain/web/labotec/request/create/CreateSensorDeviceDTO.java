package com.labotec.traccar.domain.web.labotec.request.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.labotec.traccar.domain.enums.DataType;
import com.labotec.traccar.domain.database.models.TypeSensor;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateSensorDeviceDTO {

    @JsonProperty("device_id")  // Nombre del campo JSON
    @NotNull
    private Long deviceId;

    @JsonProperty("sensor_name")  // Nombre del campo JSON
    @NotNull
    private String sensorName;

    @JsonProperty("type_sensor")  // Nombre del campo JSON
    @NotNull
    private TypeSensor typeSensor;

    @JsonProperty("data_type")  // Nombre del campo JSON
    @NotNull
    private DataType dataType;
}
