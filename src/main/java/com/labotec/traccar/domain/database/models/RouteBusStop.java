package com.labotec.traccar.domain.database.models;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteBusStop {
    private Long id;
    private Route route;
    private BusStop firstBusStop;
    private BusStop secondBusStop;
    private List<OverviewPolyline> overviewPolylines;
    private Long order;
    private Instant createdDate;
    private Instant lastModifiedDate;
}