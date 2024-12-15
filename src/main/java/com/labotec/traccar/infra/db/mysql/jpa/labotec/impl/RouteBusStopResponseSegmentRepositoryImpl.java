package com.labotec.traccar.infra.db.mysql.jpa.labotec.impl;

import com.labotec.traccar.app.ports.input.repository.RouteBusStopResponseSegmentRepository;
import com.labotec.traccar.domain.web.labotec.response.ResponseRouteBusStopSegment;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.RouteBusStopSegmentEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.response.RouteBusStopSegmentResponseMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.RouteBusStopSegmentRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class RouteBusStopResponseSegmentRepositoryImpl implements RouteBusStopResponseSegmentRepository {
    private final RouteBusStopSegmentRepositoryJpa routeBusStopSegmentRepositoryJpa;
    private final RouteBusStopSegmentResponseMapper mapper;
    @Override
    public List<ResponseRouteBusStopSegment> getSegmentsByRouteId(Long routeId) {
        List<RouteBusStopSegmentEntity> entity = routeBusStopSegmentRepositoryJpa.findByRouteId(routeId);
        return mapper.toModelList(entity);
    }
}
