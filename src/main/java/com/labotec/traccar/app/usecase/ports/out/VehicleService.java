package com.labotec.traccar.app.usecase.ports.out;

import com.labotec.traccar.app.utils.common.repository.GenericCrudService;
import com.labotec.traccar.domain.database.models.Vehicle;
import com.labotec.traccar.domain.web.dto.create.VehicleDTO;
import com.labotec.traccar.domain.web.dto.update.VehicleUpdateDTO;

public interface VehicleService extends GenericCrudService<Vehicle, VehicleDTO, VehicleUpdateDTO> {
}
