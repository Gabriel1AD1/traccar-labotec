package com.labotec.traccar.domain.database.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private List<OverviewPolyline> overviewPolylines;
    private Long order;
    @JsonIgnore
    private Instant createdDate;
    @JsonIgnore
    private Instant lastModifiedDate;
}