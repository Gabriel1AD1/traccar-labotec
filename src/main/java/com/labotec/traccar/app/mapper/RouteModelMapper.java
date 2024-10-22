package com.labotec.traccar.app.mapper;

import com.labotec.traccar.domain.database.models.Route;
import com.labotec.traccar.domain.web.dto.RouteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RouteModelMapper {
    RouteModelMapper INSTANCE = Mappers.getMapper(RouteModelMapper.class);

    Route toRoute(RouteDTO routeDTO);

}
