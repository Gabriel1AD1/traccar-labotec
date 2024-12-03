package com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper;

import com.labotec.traccar.domain.database.models.StopRegister;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.StopRegisterEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StopRegisterMapper {
    StopRegisterEntity toEntity(StopRegister domain);
    StopRegister toDomain(StopRegisterEntity entity);

    List<StopRegister> toDomainList(List<StopRegisterEntity> listByScheduleId);
}
