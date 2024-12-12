package com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper;

import com.labotec.traccar.domain.database.models.Alert;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.AlertEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AlertMapper {

    Alert toDomain(AlertEntity entity);
    AlertEntity toEntity(Alert model);
}
