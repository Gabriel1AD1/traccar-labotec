package com.labotec.traccar.app.ports.out.services;

import com.labotec.traccar.app.utils.common.repository.GenericCrudService;
import com.labotec.traccar.domain.database.models.Driver;
import com.labotec.traccar.domain.web.labotec.request.create.DriverDTO;
import com.labotec.traccar.domain.web.labotec.request.update.DriverUpdateDTO;

public interface DriverService extends GenericCrudService<Driver , DriverDTO, DriverUpdateDTO,Long> {
}
