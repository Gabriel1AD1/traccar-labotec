package com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper;

import com.labotec.traccar.domain.database.models.SensorDevice;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.SensorDeviceEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SensorDeviceMapper {

    SensorDevice toModel(SensorDeviceEntity entity);
    SensorDeviceEntity toEntity(SensorDevice model);

    List<SensorDevice> toModelList(List<SensorDeviceEntity> entityList);
    List<SensorDeviceEntity> toEntityList(List<SensorDevice> modelList);
}
