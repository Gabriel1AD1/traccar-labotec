package com.labotec.traccar.domain.database.models;

import com.labotec.traccar.domain.enums.STATE_VEHICLE;
import lombok.Data;

import java.time.Instant;

@Data
public class StateVehiclePosition {
    private Long vehicleAssociate;
    private Double latitude;
    private Double longitude;

    private BusStop lastUpdateBusStop;
    private Integer timeStoppedInMinutes;
    private STATE_VEHICLE stateVehicle;
}
