package com.labotec.traccar.app.mapper.model;

import com.labotec.traccar.domain.database.models.Schedule;
import com.labotec.traccar.domain.web.dto.labotec.request.create.ScheduleDTO;
import com.labotec.traccar.domain.web.dto.labotec.request.update.UpdateScheduleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ScheduleModelMapper {
    ScheduleModelMapper INSTANCE = Mappers.getMapper(ScheduleModelMapper.class);

    Schedule toScheduleDomain(ScheduleDTO scheduleDTO);
    Schedule toScheduleDomain(UpdateScheduleDTO scheduleDTO);

}
