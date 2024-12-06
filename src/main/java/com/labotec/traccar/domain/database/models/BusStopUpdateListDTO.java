package com.labotec.traccar.domain.database.models;

import com.labotec.traccar.domain.enums.STATE;
import lombok.Data;

@Data
public class BusStopUpdateListDTO {
    private Long id;
    private String name;
    private String description;
    private Double latitude;
    private Double longitude;
    private STATE status;

}
