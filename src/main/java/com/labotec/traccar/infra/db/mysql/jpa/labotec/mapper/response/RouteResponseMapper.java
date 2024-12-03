package com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.response;

import com.labotec.traccar.domain.web.dto.labotec.response.RouteResponse;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.RouteEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring" , uses = {RouteBusStopResponseMapper.class, RouteBusStopSegmentResponseMapper.class})
public interface RouteResponseMapper {
    RouteResponse toModel(RouteEntity routeEntity);
}
