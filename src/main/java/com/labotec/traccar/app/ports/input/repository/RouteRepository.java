package com.labotec.traccar.app.ports.input.repository;

import com.labotec.traccar.app.enums.RouteType;
import com.labotec.traccar.app.utils.common.repository.GenericRepository;
import com.labotec.traccar.domain.database.models.Route;
import com.labotec.traccar.domain.web.dto.labotec.response.RouteResponse;

import java.time.Instant;
import java.util.Optional;

public interface RouteRepository extends GenericRepository<Route,  Long> {

    Route findRouteByVehicleAndCurrentTime(long deviceId, Instant localInstant);

    Optional<RouteResponse> routeResponse(long resourceId);

    Optional<RouteType> getRouteTypeByRouteId(long routeId);


}

