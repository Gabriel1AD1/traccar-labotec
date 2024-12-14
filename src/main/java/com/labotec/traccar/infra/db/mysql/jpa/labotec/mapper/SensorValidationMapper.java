package com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper;

import com.labotec.traccar.domain.database.models.SensorValidationConfig;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.SensorValidationConfigEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SensorValidationMapper {
    SensorValidationConfigEntity toEntity(SensorValidationConfig model);
    SensorValidationConfig toModel(SensorValidationConfigEntity entity);
}
