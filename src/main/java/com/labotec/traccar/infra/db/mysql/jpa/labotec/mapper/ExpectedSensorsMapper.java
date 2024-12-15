package com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper;

import com.labotec.traccar.domain.database.models.ExpectedSensors;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.ExpectedSensorsEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExpectedSensorsMapper {

    ExpectedSensors toModel(ExpectedSensorsEntity entity);

    ExpectedSensorsEntity toEntity(ExpectedSensors model);


    List<ExpectedSensors> toModelList(List<ExpectedSensorsEntity> entity);

    List<ExpectedSensorsEntity> toEntityList(List<ExpectedSensors> model);

}
