package com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper;

import com.labotec.traccar.domain.database.models.VehicleType;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.VehicleTypeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VehicleTypeMapper {
    VehicleType toDomain(VehicleTypeEntity vehicleEntity);

    VehicleTypeEntity toEntity(VehicleType vehicleType);

}
