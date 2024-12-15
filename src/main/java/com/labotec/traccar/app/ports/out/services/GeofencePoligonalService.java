package com.labotec.traccar.app.ports.out.services;

import com.labotec.traccar.app.utils.common.repository.GenericCrudService;
import com.labotec.traccar.domain.database.models.CircularGeofence;
import com.labotec.traccar.domain.web.labotec.request.create.CircularGeofenceDTO;
import com.labotec.traccar.domain.web.labotec.request.update.GeofencePoligonalUpdateDTO;

public interface GeofencePoligonalService extends GenericCrudService<CircularGeofence, CircularGeofenceDTO, GeofencePoligonalUpdateDTO, Long> {
}
