package com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.response;


import com.labotec.traccar.domain.database.models.RouteBusStopSegment;
import com.labotec.traccar.domain.web.dto.labotec.response.RouteBusStopSegmentResponse;
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
    RouteBusStopSegmentResponse toModel(RouteBusStopSegmentEntity entity);
    List<RouteBusStopSegmentResponse> toModelList(List<RouteBusStopSegmentEntity> routeBusStopSegmentEntityList);

}

