package com.labotec.traccar.app.ports.out;

import com.labotec.traccar.app.utils.common.repository.GenericCrudService;
import com.labotec.traccar.domain.database.models.Vehicle;
import com.labotec.traccar.domain.web.dto.labotec.request.create.VehicleDTO;
import com.labotec.traccar.domain.web.dto.labotec.request.update.VehicleUpdateDTO;

public interface VehicleService extends GenericCrudService<Vehicle, VehicleDTO, VehicleUpdateDTO ,Long> {
}
