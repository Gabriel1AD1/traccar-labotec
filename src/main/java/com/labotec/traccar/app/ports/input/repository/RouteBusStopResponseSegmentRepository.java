package com.labotec.traccar.app.ports.input.repository;

import com.labotec.traccar.domain.web.labotec.response.ResponseRouteBusStopSegment;

import java.util.List;

public interface RouteBusStopResponseSegmentRepository {

    List<ResponseRouteBusStopSegment> getSegmentsByRouteId(Long routeId);
}
