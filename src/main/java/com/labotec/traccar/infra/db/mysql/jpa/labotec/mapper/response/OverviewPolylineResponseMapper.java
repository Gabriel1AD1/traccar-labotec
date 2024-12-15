package com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.response;

import com.labotec.traccar.domain.web.labotec.response.ResponseOverviewPolyline;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.OverviewPolylineEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OverviewPolylineResponseMapper {
    ResponseOverviewPolyline toModel(OverviewPolylineEntity entity);
}
