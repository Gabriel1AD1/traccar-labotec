package com.labotec.traccar.infra.db.mysql.jpa.labotec.impl;

import com.labotec.traccar.app.ports.input.repository.OverviewPolylineRepository;
import com.labotec.traccar.domain.database.models.BusStop;
import com.labotec.traccar.domain.database.models.OverviewPolyline;
import com.labotec.traccar.domain.database.models.RouteBusStop;
import com.labotec.traccar.domain.web.dto.labotec.response.ResponseOverviewPolyline;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.OverviewPolylineEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.RouteBusStopEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.OverviewPolylineMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.RouteBusStopMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.projection.OverviewPolylineProjection;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.OverviewPolylineRepositoryJpa;
import com.labotec.traccar.infra.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class OverviewPolylineRepositoryImpl implements OverviewPolylineRepository {
    private final OverviewPolylineRepositoryJpa overviewPolylineRepositoryJpa;
    private final RouteBusStopMapper routeBusStopMapper;
    private final OverviewPolylineMapper overviewPolylineMapper;
    @Override
    public Iterable<OverviewPolyline> findAllByBusStop(BusStop busStop) {
        return null;
    }

    @Override
    public OverviewPolyline findByRouteBusStopAndPrimary(RouteBusStop routeBusStopIterable) {
        RouteBusStopEntity routeBusStopEntity = routeBusStopMapper.toEntity(routeBusStopIterable);
        OverviewPolylineEntity overviewPolyline = overviewPolylineRepositoryJpa.findByRouteBusStopAndIsPrimary(routeBusStopEntity)
                .orElseThrow(
                        () -> new EntityNotFoundException("Overview not found by primary is true and bus stop id : " + routeBusStopEntity.getId())
                );
        return overviewPolylineMapper.toModel(overviewPolyline);
    }

    @Override
    public OverviewPolyline create(OverviewPolyline entity) {
        OverviewPolylineEntity overviewPolyline = overviewPolylineMapper.toEntity(entity);
        System.out.println(entity.toString());
        OverviewPolylineEntity overviewPolylineSave = overviewPolylineRepositoryJpa.save(overviewPolyline);
        System.out.println(overviewPolylineSave.toString());

        return overviewPolylineMapper.toModel(overviewPolylineSave);
    }

    @Override
    public List<ResponseOverviewPolyline> findByRouteBusStopId(Long routeBusStopId) {
        List<OverviewPolylineProjection> findAllByRoute = overviewPolylineRepositoryJpa.findByRouteBusStopId(routeBusStopId);
        List<ResponseOverviewPolyline> map =  new ArrayList<>();
        findAllByRoute.forEach(s -> map.add(ResponseOverviewPolyline.builder()
                        .id(s.getId())
                        .polyline(s.getPolyline())
                        .isPrimary(s.getIsPrimary())
                .build()) );
        return map;
    }


}
