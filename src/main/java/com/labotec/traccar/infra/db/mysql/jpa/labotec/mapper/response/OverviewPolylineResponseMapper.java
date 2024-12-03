package com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.response;

import com.labotec.traccar.domain.web.dto.labotec.response.OverviewPolylineResponse;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.OverviewPolylineEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface OverviewPolylineResponseMapper {
    @Mappings({
            @Mapping(source = "routeBusStop.id" , target = "routeBusStopId")
    })
    OverviewPolylineResponse toModel(OverviewPolylineEntity entity);
}
