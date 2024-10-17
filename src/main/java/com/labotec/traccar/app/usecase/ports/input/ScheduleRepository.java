package com.labotec.traccar.app.usecase.ports.input;

import com.labotec.traccar.app.utils.common.repository.GenericRepository;
import com.labotec.traccar.domain.database.models.Schedule;
import com.labotec.traccar.domain.web.dto.ScheduleDTO;

public interface ScheduleRepository extends GenericRepository<Schedule , Integer , ScheduleDTO> {
}
