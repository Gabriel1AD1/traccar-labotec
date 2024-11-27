package com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper;

import com.labotec.traccar.domain.database.models.DriverSchedule;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.DriverScheduleEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        CompanyMapper.class,
        DriverMapper.class,
        UserEntity.class,})
public interface DriverScheduleMapper {
    DriverScheduleEntity toEntity(DriverSchedule driverSchedule);
    @Mapping(target = "scheduleId" , ignore = true)
    DriverSchedule toModel(DriverScheduleEntity driverScheduleEntity);
}
