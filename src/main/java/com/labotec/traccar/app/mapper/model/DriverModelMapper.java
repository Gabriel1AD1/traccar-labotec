package com.labotec.traccar.app.mapper.model;

import com.labotec.traccar.domain.database.models.Driver;
import com.labotec.traccar.domain.web.labotec.request.create.DriverDTO;
import com.labotec.traccar.domain.web.labotec.request.update.DriverUpdateDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DriverModelMapper {


    // Método que recibe un objeto Company y lo mapea al campo correspondiente en Driver
    Driver toEntity(DriverDTO driverDTO);
    Driver toEntity(DriverUpdateDTO driverDTO);

    DriverDTO toDTO(Driver driver);
}
