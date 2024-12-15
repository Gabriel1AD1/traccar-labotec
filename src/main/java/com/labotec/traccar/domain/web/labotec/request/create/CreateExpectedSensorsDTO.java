package com.labotec.traccar.domain.web.labotec.request.create;

import com.labotec.traccar.domain.database.models.TypeSensor;
import com.labotec.traccar.domain.enums.DataType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
public class CreateExpectedSensorsDTO {

    @NotNull(message = "Device ID cannot be null.")
    @JsonProperty("device_id")
    private Long deviceId;

    @NotBlank(message = "Sensor name cannot be empty.")
    @Size(max = 100, message = "Sensor name cannot exceed 100 characters.")
    @JsonProperty("sensor_name")
    private String nameSensor;

    @Size(max = 255, message = "Sensor description cannot exceed 255 characters.")
    @JsonProperty("sensor_description")
    private String descriptionSensor;

    @NotNull(message = "Sensor type cannot be null.")
    @JsonProperty("sensor_type")
    private TypeSensor typeSensor;

    @NotNull(message = "Data type cannot be null.")
    @JsonProperty("data_type")
    private DataType dataType;
}
