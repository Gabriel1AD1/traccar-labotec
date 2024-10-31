package com.labotec.traccar.app.mapper;


import com.labotec.traccar.domain.database.models.Vehicle;
import com.labotec.traccar.domain.web.dto.entel.create.VehicleDTO;
import com.labotec.traccar.domain.web.dto.entel.update.VehicleUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VehicleModelMapper {
    VehicleModelMapper INSTANCE = Mappers.getMapper(VehicleModelMapper.class);

    Vehicle toVehicleModel(VehicleDTO vehicleDTO);
    Vehicle toVehicleModel(VehicleUpdateDTO vehicleDTO);
}
