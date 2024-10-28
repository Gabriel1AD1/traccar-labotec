package com.labotec.traccar.app.mapper;

import com.labotec.traccar.domain.database.models.Driver;
import com.labotec.traccar.domain.web.dto.create.DriverDTO;
import com.labotec.traccar.domain.web.dto.update.DriverUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface DriverModelMapper {

    DriverModelMapper INSTANCE = Mappers.getMapper(DriverModelMapper.class);

    // Método que recibe un objeto Company y lo mapea al campo correspondiente en Driver
    Driver toEntity(DriverDTO driverDTO);
    Driver toEntity(DriverUpdateDTO driverDTO);

    // Mapea de entidad a DTO y extrae el companyId desde el objeto Company
    @Mapping(target = "companyId", source = "company.id")
    DriverDTO toDTO(Driver driver);
}
