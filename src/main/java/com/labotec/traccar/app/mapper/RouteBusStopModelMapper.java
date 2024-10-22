package com.labotec.traccar.app.mapper;


import com.labotec.traccar.domain.database.models.BusStop;
import com.labotec.traccar.domain.database.models.Company;
import com.labotec.traccar.domain.database.models.Route;
import com.labotec.traccar.domain.database.models.RouteBusStop;
import com.labotec.traccar.domain.web.dto.RouteBusStopDTO;
import com.labotec.traccar.domain.web.dto.RouteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RouteBusStopModelMapper {
    RouteBusStopModelMapper INSTANCE = Mappers.getMapper(RouteBusStopModelMapper.class);

    RouteBusStop toRouteBusStop (RouteBusStopDTO routeBusStopDTO);
}
