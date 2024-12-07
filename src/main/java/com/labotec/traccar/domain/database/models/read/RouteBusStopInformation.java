package com.labotec.traccar.domain.database.models.read;

import lombok.Builder;
import lombok.Data;

@Data@Builder
public class RouteBusStopInformation {
    Long id;
    Long order;
    Long firstBusStopId;
    Long secondBusStopId;
}
