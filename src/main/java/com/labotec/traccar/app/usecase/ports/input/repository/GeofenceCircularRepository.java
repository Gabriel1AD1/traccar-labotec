package com.labotec.traccar.app.usecase.ports.input.repository;

import com.labotec.traccar.app.utils.common.repository.GenericRepository;
import com.labotec.traccar.domain.database.models.CircularGeofence;

public interface GeofenceCircularRepository extends GenericRepository<CircularGeofence, Integer> {

}
