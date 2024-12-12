package com.labotec.traccar.infra.db.mysql.jpa.labotec.impl;

import com.labotec.traccar.app.enums.RouteType;
import com.labotec.traccar.app.ports.input.repository.RouteRepository;
import com.labotec.traccar.domain.database.models.Route;
import com.labotec.traccar.domain.web.dto.labotec.response.ResponseRoute;
import com.labotec.traccar.domain.web.dto.labotec.response.RouteResponse;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.RouteEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.RouteMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.response.RouteResponseMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.projection.RouteResponseProjection;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.RouteRepositoryJpa;
import com.labotec.traccar.infra.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Repository
public class RouteRepositoryImpl implements RouteRepository {

    private final RouteMapper routeMapper;
    private final RouteRepositoryJpa routeRepositoryJpa;
    private final RouteResponseMapper routeResponseMapper;
    private final Logger logger = LoggerFactory.getLogger(RouteRepositoryImpl.class);
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
        return List.of() ;
    }


    @Override
    public void deleteById(Long resourceId, Long userId) {
        boolean permissionCheck = routeRepositoryJpa.existsByRouteIdAndUserId(resourceId,userId);
        if(permissionCheck) {
            routeRepositoryJpa.deleteById(resourceId);
            logger.info("Se eliminado una ruta");
        }else {
            logger.warn("No se encontró o no se elimino la ruta ");
        }
    }

    @Override
    public Route findRouteByVehicleAndCurrentTime(long deviceId, Instant localInstant) {
        RouteEntity routeEntity = routeRepositoryJpa.findRouteByVehicleAndCurrentTime(deviceId,localInstant).orElse(null);
        return routeMapper.toModel(routeEntity);
    }

    @Override
    public Optional<RouteResponse> routeResponse(long resourceId) {
        RouteEntity routeEntity = routeRepositoryJpa.findById(resourceId).orElse(null);
        RouteResponse routeResponse = routeResponseMapper.toModel(routeEntity);
        return Optional.ofNullable(routeResponse);
    }

    @Override
    public Optional<RouteType> getRouteTypeByRouteId(Long routeId) {
        RouteType findByRouteId = routeRepositoryJpa.findRouteTypeByRouteId(routeId).orElse(null);
        return Optional.ofNullable(findByRouteId);
    }

    @Override
    public List<ResponseRoute> findAllRouteByUserId(Long userId) {
        List<RouteResponseProjection> findAllRouteByUserId = routeRepositoryJpa.findRoutesByUserId(userId);

        // Usar stream para mapear y recolectar en una lista
        return findAllRouteByUserId.stream()
                .map(s -> ResponseRoute.builder()
                        .id(s.getId())
                        .routeType(s.getRouteType())
                        .name(s.getName())
                        .distanceMaxInKM(s.getDistanceMaxInKM())
                        .distanceMinInKM(s.getDistanceMinInKM())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public boolean checkRouteAndUserId(Long routeId, Long userId) {
        return routeRepositoryJpa.existsByRouteIdAndUserId(routeId,userId);
    }

}
