package com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper;

import com.labotec.traccar.domain.database.models.StateVehiclePosition;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.StateVehiclePositionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {
        VehicleMapper.class,
        BusStopMapper.class,
})
public interface StateVehiclePositionMapper {

    StateVehiclePositionEntity toEntity(StateVehiclePosition stateVehiclePosition);

    StateVehiclePosition toModel(StateVehiclePositionEntity stateVehiclePositionEntity);

}
