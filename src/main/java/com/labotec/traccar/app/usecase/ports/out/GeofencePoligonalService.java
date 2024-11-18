package com.labotec.traccar.app.usecase.ports.out;

import com.labotec.traccar.app.utils.common.repository.GenericCrudService;
import com.labotec.traccar.domain.database.models.CircularGeofence;
import com.labotec.traccar.domain.web.dto.entel.create.CircularGeofenceDTO;
import com.labotec.traccar.domain.web.dto.entel.update.GeofencePoligonalUpdateDTO;

public interface GeofencePoligonalService extends GenericCrudService<CircularGeofence, CircularGeofenceDTO, GeofencePoligonalUpdateDTO, Long> {
}
