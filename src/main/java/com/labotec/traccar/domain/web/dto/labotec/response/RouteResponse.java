package com.labotec.traccar.domain.web.dto.labotec.response;

import lombok.Data;

import java.util.List;
@Data
public class RouteResponse {
    private Long id;
    private String name;
    private List<RouteBusStopResponse> busStopsList;
    private List<RouteBusStopSegmentResponse> routeBusStopSegmentList ;
}
