package com.labotec.traccar.app.ports.input.repository;

import com.labotec.traccar.domain.web.dto.labotec.response.RouteBusStopSegmentResponse;

import java.util.List;

public interface RouteBusStopResponseSegmentRepository {

    List<RouteBusStopSegmentResponse> getSegmentsByRouteId(Long routeId);
}
