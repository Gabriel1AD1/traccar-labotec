package com.labotec.traccar.domain.database.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteBusStop {
    private Long id;
    @JsonIgnore
    private Route route;
    private BusStop firstBusStop;
    private BusStop secondBusStop;
    private Integer estimatedTravelTime; // En minutos
    private Integer maxWaitTime; // En minutos
    private Integer minWaitTime; // En minutos
    private List<OverviewPolyline> overviewPolylines;
    private Long order;
    private Instant createdDate;
    private Instant lastModifiedDate;
}