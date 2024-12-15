package com.labotec.traccar.domain.web.labotec.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.labotec.traccar.domain.database.models.TypeSensor;
import com.labotec.traccar.domain.enums.DataType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseExpectedSensor {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name_sensor")
    private String nameSensor;
    @JsonProperty("description_sensor")
    private String descriptionSensor;
    @JsonProperty("type_sensor")
    private TypeSensor typeSensor;
    @JsonProperty("data_type")
    private DataType dataType;
}
