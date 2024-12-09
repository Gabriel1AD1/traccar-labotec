package com.labotec.traccar.app.ports.input.repository;

import com.labotec.traccar.app.utils.common.repository.GenericRepository;
import com.labotec.traccar.domain.database.models.Vehicle;

public interface VehicleRepository extends GenericRepository<Vehicle,Long> {

    Vehicle findByDevice(Long id);
    Vehicle findByLicencePlate(String licencePlate);
}
