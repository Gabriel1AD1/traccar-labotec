package com.labotec.traccar.infra.db.mysql.jpa.labotec.impl;

import com.labotec.traccar.app.ports.input.repository.RouteBusStopRepository;
import com.labotec.traccar.domain.database.models.Route;
import com.labotec.traccar.domain.database.models.RouteBusStop;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.RouteBusStopEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.RouteEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.RouteBusStopMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.RouteMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.RouteBusStopRepositoryJpa;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.RouteRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class RouteBusStopRepositoryImpl implements RouteBusStopRepository {

    private final RouteBusStopRepositoryJpa routeBusStopRepositoryJpa;
    private final RouteRepositoryJpa routeRepositoryJpa;
    private final RouteBusStopMapper routeBusStopMapper;
    private final RouteMapper routeMapper;
    @Override
    public RouteBusStop create(RouteBusStop entity) {
        RouteBusStopEntity routeBusStopEntity = routeBusStopMapper.toEntity(entity);
        RouteBusStopEntity routeBusStopEntitySaved = routeBusStopRepositoryJpa.save(routeBusStopEntity);
        return routeBusStopMapper.toModel(routeBusStopEntitySaved);
    }

    @Override
    public RouteBusStop findById(Long resourceId, Long userId) {
        return null;
    }

    @Override
    public Optional<RouteBusStop> findByIdOptional(Long resourceId, Long userId) {
        return Optional.empty();
    }

    @Override
    public Iterable<RouteBusStop> findAll(Long userId) {
        return null;
    }

    @Override
    public RouteBusStop update(RouteBusStop entity) {
        return null;
    }

    @Override
    public void deleteById(Long resourceId, Long userId) {

    }

    @Override
    public Iterable<RouteBusStop> findByRoute(Route routeId) {
        RouteEntity routeEntity = routeMapper.toEntity(routeId);
        Iterable<RouteBusStopEntity> routeBusStopEntity = routeBusStopRepositoryJpa.findByRoute(routeEntity);

        return routeBusStopMapper.toModelIterable(routeBusStopEntity);
    }

    @Override
    public List<RouteBusStop> findByRouteOrderByOrder(Route routeId) {
        RouteEntity routeEntity = routeMapper.toEntity(routeId);
        List<RouteBusStopEntity> routeBusStopEntities = routeBusStopRepositoryJpa.findByRouteOrderByOrder(routeEntity);

        return routeBusStopMapper.toModelList(routeBusStopEntities);
    }

    @Override
    public Iterable<RouteBusStop> createList(List<RouteBusStop> routeBusStops) {
        List<RouteBusStopEntity> routeBusStopEntities = routeBusStopMapper.toEntityList(routeBusStops);
        List<RouteBusStopEntity> routeBusStopEntitiesSave = routeBusStopRepositoryJpa.saveAll(routeBusStopEntities);
        return routeBusStopMapper.toModelIterable(routeBusStopEntitiesSave);
    }
}
