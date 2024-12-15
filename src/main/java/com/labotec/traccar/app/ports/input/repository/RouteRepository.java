package com.labotec.traccar.app.ports.input.repository;

import com.labotec.traccar.app.enums.RouteType;
import com.labotec.traccar.app.utils.common.repository.GenericRepository;
import com.labotec.traccar.domain.database.models.Route;
import com.labotec.traccar.domain.web.labotec.response.ResponseRoute;
import com.labotec.traccar.domain.web.labotec.response.RouteResponse;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface RouteRepository extends GenericRepository<Route,  Long> {

    Route findRouteByVehicleAndCurrentTime(long deviceId, Instant localInstant);

    Optional<RouteResponse> routeResponse(long resourceId);

    Optional<RouteType> getRouteTypeByRouteId(Long routeId);

    List<ResponseRoute> findAllRouteByUserId(Long userId);

    boolean checkRouteAndUserId(Long routeId, Long userId);
}

