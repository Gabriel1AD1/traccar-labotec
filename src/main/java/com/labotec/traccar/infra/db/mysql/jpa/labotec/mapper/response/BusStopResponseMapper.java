package com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.response;

import com.labotec.traccar.domain.web.labotec.response.BusStopResponse;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.BusStopEntity;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface BusStopResponseMapper {
    BusStopResponse toModel(BusStopEntity busStopEntity);
}
