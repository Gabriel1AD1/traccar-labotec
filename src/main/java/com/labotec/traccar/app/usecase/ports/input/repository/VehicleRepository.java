package com.labotec.traccar.app.usecase.ports.input.repository;

import com.labotec.traccar.app.utils.common.repository.GenericRepository;
import com.labotec.traccar.domain.database.models.Vehicle;

public interface VehicleRepository extends GenericRepository<Vehicle,Integer> {

    Vehicle findByDevice(Integer id);
}
