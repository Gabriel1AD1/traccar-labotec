package com.labotec.traccar.app.usecase.ports.input;

import com.labotec.traccar.app.utils.common.repository.GenericRepository;
import com.labotec.traccar.domain.database.models.Vehicle;
import com.labotec.traccar.domain.web.dto.VehicleDTO;

public interface VehicleRepository extends GenericRepository<Vehicle,Integer, VehicleDTO> {
}
