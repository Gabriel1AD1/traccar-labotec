package com.labotec.traccar.app.implementation;

import com.labotec.traccar.app.ports.input.repository.ScheduleRepository;
import com.labotec.traccar.app.ports.input.repository.VehicleRepository;
import com.labotec.traccar.domain.database.models.Schedule;
import com.labotec.traccar.domain.database.models.Vehicle;
import com.labotec.traccar.domain.web.dto.traccar.DeviceRequestDTO;
import lombok.AllArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
public class ProcessRoute {
    private final ScheduleRepository scheduleRepository;
    private final VehicleRepository vehicleRepository;

    public void validateRoute(DeviceRequestDTO deviceRequestDTO){
        //Obtenemos la programación de un vehiculo por su id
        Schedule schedule = scheduleRepository.findByVehicleIdAndInstantNow(deviceRequestDTO.getDeviceId(), Instant.now());

    }
}
