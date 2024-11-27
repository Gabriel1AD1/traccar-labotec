package com.labotec.traccar.domain.query;


import lombok.Data;

import java.util.List;

@Data
public class ScheduleRouteProjection {
    private Long id;
    private List<ScheduleRouteBusStopProjection> busStopsList;
    private String name;
}
