package com.labotec.traccar.app.mapper;

import com.labotec.traccar.domain.database.models.Schedule;
import com.labotec.traccar.domain.web.dto.ScheduleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ScheduleModelMapper {
    ScheduleModelMapper INSTANCE = Mappers.getMapper(ScheduleModelMapper.class);

    Schedule toScheduleDomain(ScheduleDTO scheduleDTO);

}
