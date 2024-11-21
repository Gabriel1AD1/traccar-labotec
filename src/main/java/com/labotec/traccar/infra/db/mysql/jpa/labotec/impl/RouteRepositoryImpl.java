package com.labotec.traccar.infra.db.mysql.jpa.labotec.impl;

import com.labotec.traccar.app.usecase.ports.input.repository.RouteRepository;
import com.labotec.traccar.domain.database.models.Route;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.RouteEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.RouteMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.RouteRepositoryJpa;
import com.labotec.traccar.infra.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.labotec.traccar.infra.db.mysql.jpa.labotec.message.RouteMessage.ROUTE_NOT_FOUND_BY_ID;

@AllArgsConstructor
@Repository
public class RouteRepositoryImpl implements RouteRepository {

    private final RouteMapper routeMapper;
    private final RouteRepositoryJpa routeRepositoryJpa;

    @Override
    public Route create(Route entity) {
        RouteEntity routeEntity = routeMapper.toEntity(entity);
        RouteEntity routeSaved = routeRepositoryJpa.save(routeEntity);
        return routeMapper.toModel(routeSaved);
    }
    @Override
    public Route update(Route entity) {
        RouteEntity routeEntity = routeMapper.toEntity(entity);
        RouteEntity routeSaved = routeRepositoryJpa.save(routeEntity);
        return routeMapper.toModel(routeSaved);
    }
    @Override
    public Route findById(Long resourceId, Long userId) {
        RouteEntity routeEntity = routeRepositoryJpa.findRouteByUserIdAndRouteId(userId , resourceId).orElseThrow(
                ()-> new EntityNotFoundException("El recurso buscado no ha sido encontrado "+ resourceId)
        );
        return routeMapper.toModel(routeEntity);
    }

    @Override
    public Optional<Route> findByIdOptional(Long resourceId, Long userId) {
        return Optional.empty();
    }

    @Override
    public Iterable<Route> findAll(Long userId) {
        return null;
    }


    @Override
    public void deleteById(Long resourceId, Long userId) {

    }

}
