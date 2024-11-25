package com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper;

import com.labotec.traccar.domain.database.models.DriverSchedule;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.DriverScheduleEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {
        CompanyMapper.class,
        DriverMapper.class,
        UserEntity.class,
        ScheduleMapper.class})
public interface DriverScheduleMapper {

    DriverScheduleEntity toEntity(DriverSchedule driverSchedule);

    DriverSchedule toModel(DriverScheduleEntity driverScheduleEntity);
}
