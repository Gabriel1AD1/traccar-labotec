package com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.response;


import com.labotec.traccar.domain.web.labotec.response.ResponseRouteBusStopSegment;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.RouteBusStopSegmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RouteBusStopSegmentResponseMapper {
    @Mappings({
            @Mapping(source = "route.id",target = "routeId"),
            @Mapping(source = "busStop.id",target = "busStopId")
    })
    ResponseRouteBusStopSegment toModel(RouteBusStopSegmentEntity entity);
    List<ResponseRouteBusStopSegment> toModelList(List<RouteBusStopSegmentEntity> routeBusStopSegmentEntityList);

}

