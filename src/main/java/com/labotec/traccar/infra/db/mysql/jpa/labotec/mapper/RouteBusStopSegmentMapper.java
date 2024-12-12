package com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper;

import com.labotec.traccar.domain.database.models.RouteBusStopSegment;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.RouteBusStopSegmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,uses = {
        BusStopMapper.class,
        RouteBusStopMapper.class,
        OverviewPolylineMapper.class
})
public interface RouteBusStopSegmentMapper {
    RouteBusStopSegment toModel(RouteBusStopSegmentEntity routeBusStopSegmentEntity);
    RouteBusStopSegmentEntity toEntity(RouteBusStopSegment routeBusStopSegment);


}