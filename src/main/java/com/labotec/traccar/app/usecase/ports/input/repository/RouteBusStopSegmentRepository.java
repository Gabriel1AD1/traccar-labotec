package com.labotec.traccar.app.usecase.ports.input.repository;

import com.labotec.traccar.domain.database.models.RouteBusStopSegment;

public interface RouteBusStopSegmentRepository {

    RouteBusStopSegment create(RouteBusStopSegment routeBusStopSegment);

    RouteBusStopSegment update(RouteBusStopSegment routeBusStopSegment);
}
