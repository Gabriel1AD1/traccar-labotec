package com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper;

import com.labotec.traccar.domain.database.models.OverviewPolyline;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.OverviewPolylineEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {RouteBusStopMapper.class})
public interface OverviewPolylineMapper {
    OverviewPolyline toModel(OverviewPolylineEntity overviewPolyline);

    OverviewPolylineEntity toEntity(OverviewPolyline entity);
}
