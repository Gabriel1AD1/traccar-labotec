package com.labotec.traccar.domain.web.dto.labotec.response;

import com.labotec.traccar.domain.database.models.BusStop;
import lombok.Data;

import java.util.List;

@Data
public class ResponseRouteBusStop {
    private Long id;
    private Long routeId;
    private Long firstBusStopId;
    private Long secondBusStopId;
    private List<ResponseOverviewPolyline> overviewPolylines;
}
