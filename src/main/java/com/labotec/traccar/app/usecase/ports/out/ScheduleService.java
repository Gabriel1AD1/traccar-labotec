package com.labotec.traccar.app.usecase.ports.out;

import com.labotec.traccar.app.utils.common.repository.GenericCrudService;
import com.labotec.traccar.domain.database.models.Schedule;
import com.labotec.traccar.domain.database.models.Vehicle;
import com.labotec.traccar.domain.web.dto.create.ScheduleDTO;
import com.labotec.traccar.domain.web.dto.update.ScheduleUpdateDTO;

import java.time.Instant;
import java.util.List;

public interface ScheduleService extends GenericCrudService<Schedule , ScheduleDTO , ScheduleUpdateDTO> {

    Schedule updateStatus(Integer id, Byte status);

    List<Schedule> findAllByDateRange(Instant from, Instant to);

    Schedule updateEstimatedDepartureTime(Integer id, Instant estimatedDepartureTime);

    Schedule updateEstimatedArrivalTime(Integer id, Instant estimatedDepartureTime);

    List<Schedule> findScheduleByVehicle(Integer vehicle);


}
