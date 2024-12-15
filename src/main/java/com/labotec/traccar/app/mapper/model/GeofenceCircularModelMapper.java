package com.labotec.traccar.app.mapper.model;

import com.labotec.traccar.domain.database.models.CircularGeofence;
import com.labotec.traccar.domain.web.labotec.request.create.CircularGeofenceDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GeofenceCircularModelMapper {

    CircularGeofence toGeofencePoligonal(CircularGeofenceDTO circularGeofenceDTO);

}
