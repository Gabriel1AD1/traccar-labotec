package com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper;

import com.labotec.traccar.domain.database.models.AlertValidParams;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.AlertValidParamsEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AlertValidParamsMapper {
    AlertValidParamsEntity toEntity(AlertValidParams model);
    AlertValidParams toModel(AlertValidParamsEntity entity);
    List<AlertValidParams> toModelList(List<AlertValidParamsEntity> entity);

}
