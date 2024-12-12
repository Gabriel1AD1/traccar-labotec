package com.labotec.traccar.domain.database.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.labotec.traccar.domain.enums.TypeBusStop;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteBusStopSegment {
    private Long id;
    @JsonIgnore
    private Route route;
    private BusStop busStop;
    private Integer estimatedTravelTime; // En minutos
    private Integer maxWaitTime; // En minutos
    private Integer minWaitTime; // En minutos
    private Long order;
    private TypeBusStop typeBusStop;

}
