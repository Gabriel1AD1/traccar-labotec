package com.labotec.traccar.app.ports.out.services;

import com.labotec.traccar.app.utils.common.repository.GenericCrudService;
import com.labotec.traccar.domain.database.models.Vehicle;
import com.labotec.traccar.domain.web.labotec.request.create.VehicleDTO;
import com.labotec.traccar.domain.web.labotec.request.update.VehicleUpdateDTO;
import com.labotec.traccar.domain.web.labotec.response.ResponseVehicle;

import java.util.List;

public interface VehicleService extends GenericCrudService<Vehicle, VehicleDTO, VehicleUpdateDTO ,Long> {
    List<ResponseVehicle> findAllByUserId(Long userId);
}
