package com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper;

import com.labotec.traccar.domain.database.models.ProgressRoute;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.ProgressRouteE;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProgressRouteMapper {

    ProgressRouteE toEntity(ProgressRoute progressRoute);

    ProgressRoute toModel(ProgressRouteE progressRouteE);

}
