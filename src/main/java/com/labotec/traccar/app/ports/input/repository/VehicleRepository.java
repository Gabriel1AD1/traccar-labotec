package com.labotec.traccar.app.ports.input.repository;

import com.labotec.traccar.app.utils.common.repository.GenericRepository;
import com.labotec.traccar.domain.database.models.Vehicle;
import com.labotec.traccar.domain.web.dto.labotec.response.ResponseVehicle;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface VehicleRepository extends GenericRepository<Vehicle,Long> {

    Vehicle findByDevice(Long id);
    Vehicle findByLicencePlate(String licencePlate);
    String getLicencePlateByDeviceId(Long traccarDeviceId);
    List<ResponseVehicle> findAllByUserId(@NotNull Long userId);
}
