package com.labotec.traccar.domain.web.labotec.request.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.labotec.traccar.app.Validations.ValidValueForDataType;
import com.labotec.traccar.domain.enums.DataType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "DTO for configuring static validation rules for sensors.")
public class CreateSensorStaticValidationConfigDTO {

    @JsonProperty("name_validation")
    @NotNull
    @Schema(description = "Name of the validation rule.", example = "TemperatureRangeValidation")
    private String nameValidation;

    @JsonProperty("device_id")
    @NotNull(message = "Device ID is required.")
    @Schema(description = "The unique ID of the device.", example = "83")
    private Long deviceId;

    @JsonProperty("sensor_id")
    @NotNull(message = "Sensor ID is required.")
    @Schema(description = "The unique ID of the sensor.", example = "102")
    private Long sensorId;

    @JsonProperty("operator")
    @NotNull(message = "Operator is required.")
    @Schema(
            description = "The operator used to compare the value. Available operators are '=', '>', '<', '>=', '<='.",
            example = ">"
    )
    private String operator;

    @JsonProperty("value")
    @NotNull(message = "Value is required.")
    @ValidValueForDataType(message = "The value does not match the data type.")
    @Schema(description = "The value to be compared, depending on the data type.", example = "75.5")
    private String value;

    @JsonProperty("message_alert")
    @NotNull(message = "Alert message is required.")
    @Schema(description = "The alert message triggered when the validation fails.", example = "Temperature is out of range!")
    private String messageAlert;

    @JsonProperty("data_type")
    @NotNull(message = "Data type is required.")
    @Schema(description = "The data type of the sensor value.", example = "DOUBLE")
    private DataType dataType;
}
