package com.labotec.traccar.app.mapper;

import com.labotec.traccar.domain.database.models.Driver;
import com.labotec.traccar.domain.web.dto.entel.create.DriverDTO;
import com.labotec.traccar.domain.web.dto.entel.update.DriverUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface DriverModelMapper {


    // Método que recibe un objeto Company y lo mapea al campo correspondiente en Driver
    Driver toEntity(DriverDTO driverDTO);
    Driver toEntity(DriverUpdateDTO driverDTO);

    DriverDTO toDTO(Driver driver);
}
