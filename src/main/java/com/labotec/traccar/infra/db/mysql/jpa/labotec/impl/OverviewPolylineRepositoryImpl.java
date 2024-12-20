package com.labotec.traccar.infra.db.mysql.jpa.labotec.impl;

import com.labotec.traccar.app.ports.input.repository.OverviewPolylineRepository;
import com.labotec.traccar.domain.database.models.BusStop;
import com.labotec.traccar.domain.database.models.OverviewPolyline;
import com.labotec.traccar.domain.database.models.RouteBusStop;
import com.labotec.traccar.domain.query.OptimizedOverviewPolyline;
import com.labotec.traccar.domain.web.labotec.response.ResponseOverviewPolyline;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.OverviewPolylineEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.RouteBusStopEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.OverviewPolylineMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.RouteBusStopMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.projection.OptimizedOverviewPolylineProjection;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.projection.OverviewPolylineProjection;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.OverviewPolylineRepositoryJpa;
import com.labotec.traccar.app.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        System.out.println(overviewPolylineSave);

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

    @Override
    public List<OptimizedOverviewPolyline> findPrimaryPolylinesByRouteId(Long routeId) {
        // Llamamos al repositorio para obtener las proyecciones
        List<OptimizedOverviewPolylineProjection> getPolyline = overviewPolylineRepositoryJpa.findPrimaryPolylinesByRouteIdWithProjection(routeId);

        // Usamos stream() y map() para transformar las proyecciones en los objetos OptimizedOverviewPolyline
        return getPolyline.stream()
                .map(s -> OptimizedOverviewPolyline.builder()
                        .polyline(s.getPolyline())
                        .build())
                .collect(Collectors.toList()); // Recolectamos en una lista
    }



}
