package com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.response;

import com.labotec.traccar.domain.web.dto.labotec.response.RouteBusStopResponse;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.RouteBusStopEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
@Mapper(componentModel = "spring",uses = {OverviewPolylineResponseMapper.class})
public interface RouteBusStopResponseMapper {
    @Mappings({
            @Mapping(source = "route.id" ,target = "routeId"),
            @Mapping(source = "firstBusStop.id" , target = "firstBusStopId"),
            @Mapping(source = "secondBusStop.id" , target = "secondBusStopId")
    })
    RouteBusStopResponse toModel(RouteBusStopEntity entity);
}
