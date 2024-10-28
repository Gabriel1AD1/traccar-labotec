package com.labotec.traccar.app.mapper;

import com.labotec.traccar.domain.database.models.Location;
import com.labotec.traccar.domain.web.dto.create.LocationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface LocationModelMapper {
    LocationModelMapper INSTANCE = Mappers.getMapper(LocationModelMapper.class);

    // Mapeo de DTO a Entidad, con Company como parámetro
    Location toEntity(LocationDTO locationDTO);

    // Mapeo de Entidad a DTO, extrayendo el companyId desde el objeto Company
    LocationDTO toDTO(Location location);
}
