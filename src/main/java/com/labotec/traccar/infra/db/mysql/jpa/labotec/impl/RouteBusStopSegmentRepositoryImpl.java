package com.labotec.traccar.infra.db.mysql.jpa.labotec.impl;

import com.labotec.traccar.app.ports.input.repository.RouteBusStopSegmentRepository;
import com.labotec.traccar.domain.database.models.RouteBusStopSegment;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.RouteBusStopSegmentMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.RouteBusStopSegmentRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@AllArgsConstructor
@Repository
public class RouteBusStopSegmentRepositoryImpl implements RouteBusStopSegmentRepository {
    private final RouteBusStopSegmentRepositoryJpa routeBusStopSegmentRepositoryJpa;
    private final RouteBusStopSegmentMapper routeBusStopSegmentMapper;
    @Override
    public RouteBusStopSegment create(RouteBusStopSegment routeBusStopSegment) {
        return routeBusStopSegmentMapper.toModel(routeBusStopSegmentRepositoryJpa.save(routeBusStopSegmentMapper.toEntity(routeBusStopSegment)));
    }

    @Override
    public RouteBusStopSegment update(RouteBusStopSegment routeBusStopSegment) {
        return routeBusStopSegmentMapper.toModel(routeBusStopSegmentRepositoryJpa.save(routeBusStopSegmentMapper.toEntity(routeBusStopSegment)));
    }
}
