package com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper;

import com.labotec.traccar.domain.database.models.OverviewPolyline;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.OverviewPolylineEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OverviewPolylineMapper {
    @Mapping(target = "routeBusStop" , ignore = true)
    OverviewPolyline toModel(OverviewPolylineEntity overviewPolyline);
    OverviewPolylineEntity toEntity(OverviewPolyline entity);
    List<OverviewPolyline> toModelList(List<OverviewPolylineEntity> overviewPolyline);
    List<OverviewPolylineEntity> toEntityList(List<OverviewPolyline> entity);
}
