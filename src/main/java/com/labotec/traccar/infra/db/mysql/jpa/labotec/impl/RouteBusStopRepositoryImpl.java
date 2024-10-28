package com.labotec.traccar.infra.db.mysql.jpa.labotec.impl;

import com.labotec.traccar.app.usecase.ports.input.repository.RouteBusStopRepository;
import com.labotec.traccar.domain.database.models.Route;
import com.labotec.traccar.domain.database.models.RouteBusStop;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.RouteBusStopEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.RouteEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.RouteBusStopMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.RouteMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.RouteBusStopRepositoryJpa;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.RouteRepositoryJpa;
import com.labotec.traccar.infra.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.labotec.traccar.infra.db.mysql.jpa.labotec.message.RouteBusStopMessage.ROUTE_BUS_STOP_NOT_FOUND_BY_ID;

@Repository
@AllArgsConstructor
public class RouteBusStopRepositoryImpl implements RouteBusStopRepository {

    private final RouteBusStopRepositoryJpa routeBusStopRepositoryJpa;
    private final RouteRepositoryJpa routeRepositoryJpa;
    private final RouteBusStopMapper routeBusStopMapper;
    private final RouteMapper routeMapper;
    @Override
    public RouteBusStop create(RouteBusStop entity) {
        System.out.println("------------------------------------------------");
        System.out.println("------------------------------------------------");
        System.out.println("------------------------------------------------");
        System.out.println("------------------------------------------------");
        System.out.println("------------------------------------------------");
        System.out.println("------------------------------------------------");
        System.out.println("------------------------------------------------");


        System.out.println(entity.getOrder());
        RouteBusStopEntity routeBusStopEntity = routeBusStopMapper.toEntity(entity);
        System.out.println(routeBusStopEntity.toString());
        System.out.println("------------------------------------------------");
        System.out.println(routeBusStopEntity.getOrder());
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
