package com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper;

import com.labotec.traccar.domain.database.models.CircularGeofence;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.CircularGeofenceEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GeofenceCircularMapper {

    // Mapeo para geocercas poligonales
    CircularGeofence toModel(CircularGeofenceEntity entity);
    CircularGeofenceEntity toEntity(CircularGeofence model);

    List<CircularGeofence> toModelList(List<CircularGeofenceEntity> entities);
    List<CircularGeofenceEntity> toEntityList(List<CircularGeofence> models);


}