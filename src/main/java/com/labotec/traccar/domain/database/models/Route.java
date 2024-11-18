package com.labotec.traccar.domain.database.models;

import com.labotec.traccar.domain.enums.STATE;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Route {
    private Long id;
    private User userId;
    private Company companyId;
    private String name;
    private List<RouteBusStop> busStopsList;
    private BusStop originBusStop;
    private BusStop destinationBusStop;
    private Instant lastModifiedDate;
    private Instant createdDate;
}
