package com.labotec.traccar.app.ports.input.repository;

import com.labotec.traccar.app.utils.common.repository.GenericRepository;
import com.labotec.traccar.domain.database.models.Route;

import java.time.Instant;

public interface RouteRepository extends GenericRepository<Route,  Long> {

    Route findRouteByVehicleAndCurrentTime(long deviceId, Instant localInstant);
}
