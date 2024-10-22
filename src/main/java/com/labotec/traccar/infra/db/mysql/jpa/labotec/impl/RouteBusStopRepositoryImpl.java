package com.labotec.traccar.infra.db.mysql.jpa.labotec.impl;

import com.labotec.traccar.app.usecase.ports.input.RouteBusStopRepository;
import com.labotec.traccar.domain.database.models.RouteBusStop;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.RouteBusStopEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.RouteEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.RouteBusStopMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.RouteBusStopRepositoryJpa;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.RouteRepositoryJpa;
import com.labotec.traccar.infra.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.labotec.traccar.infra.db.mysql.jpa.labotec.message.RouteBusStopMessage.ROUTE_BUS_STOP_NOT_FOUND_BY_ID;
import static com.labotec.traccar.infra.db.mysql.jpa.labotec.message.RouteMessage.ROUTE_NOT_FOUND_BY_ID;

@Repository
@AllArgsConstructor
public class RouteBusStopRepositoryImpl implements RouteBusStopRepository {

    private final RouteBusStopRepositoryJpa routeBusStopRepositoryJpa;
    private final RouteRepositoryJpa routeRepositoryJpa;
    private final RouteBusStopMapper routeBusStopMapper;

    @Override
    public RouteBusStop create(RouteBusStop entity) {
        RouteBusStopEntity routeBusStopEntity = routeBusStopMapper.toEntity(entity);
        RouteBusStopEntity routeBusStopEntitySaved = routeBusStopRepositoryJpa.save(routeBusStopEntity);
        return routeBusStopMapper.toModel(routeBusStopEntitySaved);
    }

    @Override
    public RouteBusStop findById(Integer id) {
        RouteBusStopEntity routeBusStopEntity = routeBusStopRepositoryJpa.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ROUTE_BUS_STOP_NOT_FOUND_BY_ID + id));

        System.out.println(routeBusStopEntity.toString());
        return routeBusStopMapper.toModel(routeBusStopEntity);
    }

    @Override
    public Optional<RouteBusStop> findByIdOptional(Integer id) {
        return routeBusStopRepositoryJpa.findById(id)
                .map(routeBusStopMapper::toModel);
    }

    @Override
    public Iterable<RouteBusStop> findAll() {
        List<RouteBusStopEntity> routeBusStopEntities = routeBusStopRepositoryJpa.findAll();
        return routeBusStopMapper.toModelList(routeBusStopEntities);
    }

    @Override
    public RouteBusStop update(RouteBusStop entity) {
        RouteBusStopEntity routeBusStopEntity = routeBusStopMapper.toEntity(entity);
        RouteBusStopEntity routeBusStopEntitySaved = routeBusStopRepositoryJpa.save(routeBusStopEntity);
        return routeBusStopMapper.toModel(routeBusStopEntitySaved);
    }

    @Override
    public void deleteById(Integer id) {
        routeBusStopRepositoryJpa.deleteById(id);
    }

    @Override
    public Iterable<RouteBusStop> findByRoute(Integer routeId) {
        RouteEntity routeEntity = routeRepositoryJpa.findById(routeId).orElseThrow(
                ()-> new EntityNotFoundException(ROUTE_NOT_FOUND_BY_ID + routeId )
        );
        Iterable<RouteBusStopEntity> routeBusStopEntity = routeBusStopRepositoryJpa.findByRoute(routeEntity);

        return routeBusStopMapper.toModelIterable(routeBusStopEntity);
    }

    @Override
    public Iterable<RouteBusStop> createList(List<RouteBusStop> routeBusStops) {
        List<RouteBusStopEntity> routeBusStopEntities = routeBusStopMapper.toEntityList(routeBusStops);
        List<RouteBusStopEntity> routeBusStopEntitiesSave = routeBusStopRepositoryJpa.saveAll(routeBusStopEntities);
        return routeBusStopMapper.toModelIterable(routeBusStopEntitiesSave);
    }
}
