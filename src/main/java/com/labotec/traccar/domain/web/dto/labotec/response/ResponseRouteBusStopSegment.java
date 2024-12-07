package com.labotec.traccar.domain.web.dto.labotec.response;

import com.labotec.traccar.domain.enums.TYPE_BUS_STOP;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ResponseRouteBusStopSegment {
    private Long id;
    private Long routeId;
    private Long busStopId;
    private Integer estimatedTravelTime; // En minutos
    private Integer maxWaitTime; // En minutos
    private Integer minWaitTime; // En minutos
    private Long order;
    private TYPE_BUS_STOP typeBusStop;
}
