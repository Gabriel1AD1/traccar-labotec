package com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper;

import com.labotec.traccar.domain.database.models.VehiclePosition;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.VehiclePositionEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface VehiclePositionMapper {
    VehiclePositionEntity toEntity(VehiclePosition vehiclePosition);

    VehiclePosition toDomain(VehiclePositionEntity vehiclePositionEntity);



}