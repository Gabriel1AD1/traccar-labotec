package com.labotec.traccar.infra.db.mysql.jpa.labotec.impl;

import com.labotec.traccar.app.usecase.ports.input.RouteRepository;
import com.labotec.traccar.domain.database.models.Route;
import com.labotec.traccar.domain.web.dto.RouteDTO;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.BusStopEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.CompanyEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.RouteBusStopEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.RouteEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.RouteMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.BusStopRepositoryJpa;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.CompanyRepositoryJpa;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.RouteBusStopRepositoryJpa;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.RouteRepositoryJpa;
import com.labotec.traccar.infra.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.labotec.traccar.infra.db.mysql.jpa.labotec.message.ComapanyMessage.COMPANY_NOT_FOUND_BY_ID;
import static com.labotec.traccar.infra.db.mysql.jpa.labotec.message.RouteMessage.ROUTE_NOT_FOUND_BY_ID;
import static com.labotec.traccar.infra.db.mysql.jpa.labotec.message.BusStopMessage.BUS_STOP_NOT_FOUND_BY_ID;

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
    public Route findById(Integer id) {
        RouteEntity routeEntity = routeRepositoryJpa.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ROUTE_NOT_FOUND_BY_ID + id));
        return routeMapper.toModel(routeEntity);
    }

    @Override
    public Optional<Route> findByIdOptional(Integer id) {
        return routeRepositoryJpa.findById(id)
                .map(routeMapper::toModel); // Usar el mapper para convertir de entidad a modelo
    }

    @Override
    public List<Route> findAll() {
        List<RouteEntity> routeEntities = routeRepositoryJpa.findAll();
        return routeMapper.toModelList(routeEntities); // Usar el mapper para convertir la lista de entidades
    }

    @Override
    public Route update(Route entity) {
        RouteEntity routeEntity = routeMapper.toEntity(entity);
        RouteEntity routeSaved = routeRepositoryJpa.save(routeEntity);
        return routeMapper.toModel(routeSaved);
    }

    @Override
    public void deleteById(Integer id) {
        routeRepositoryJpa.deleteById(id);
    }
}
