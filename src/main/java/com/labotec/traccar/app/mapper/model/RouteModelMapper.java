package com.labotec.traccar.app.mapper.model;

import com.labotec.traccar.domain.database.models.Route;
import com.labotec.traccar.domain.web.labotec.request.create.RouteCreateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RouteModelMapper {
    RouteModelMapper INSTANCE = Mappers.getMapper(RouteModelMapper.class);

    Route toRoute(RouteCreateDTO routeCreateDTO);

}
