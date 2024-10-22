package com.labotec.traccar.app.usecase.ports.input;

import com.labotec.traccar.app.utils.common.repository.GenericRepository;
import com.labotec.traccar.domain.database.models.Schedule;
import com.labotec.traccar.domain.web.dto.ScheduleDTO;

import java.time.Instant;
import java.util.List;

public interface ScheduleRepository extends GenericRepository<Schedule , Integer> {

    Schedule updateStatus(Integer id, Byte status);

    List<Schedule> findAllByDateRange(Instant from, Instant to);

    Schedule updateEstimatedDepartureTime(Integer id, Instant estimatedDepartureTime);

    Schedule updateEstimatedArrivalTime(Integer id, Instant estimatedArrivalTime);

    List<Schedule> findTripsByVehicleId(Integer vehicleId);
}
