package com.labotec.traccar.app.mapper.model;


import com.labotec.traccar.domain.database.models.Vehicle;
import com.labotec.traccar.domain.web.labotec.request.create.VehicleDTO;
import com.labotec.traccar.domain.web.labotec.request.update.VehicleUpdateDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VehicleModelMapper {

    Vehicle toVehicleModel(VehicleDTO vehicleDTO);
    Vehicle toVehicleModel(VehicleUpdateDTO vehicleDTO);
}
