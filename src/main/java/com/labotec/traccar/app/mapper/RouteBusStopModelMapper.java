package com.labotec.traccar.app.mapper;


import com.labotec.traccar.domain.database.models.RouteBusStop;
import com.labotec.traccar.domain.web.dto.create.RouteBusStopDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RouteBusStopModelMapper {
    RouteBusStopModelMapper INSTANCE = Mappers.getMapper(RouteBusStopModelMapper.class);

    RouteBusStop toRouteBusStop (RouteBusStopDTO routeBusStopDTO);




}
