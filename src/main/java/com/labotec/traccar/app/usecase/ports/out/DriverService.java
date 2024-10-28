package com.labotec.traccar.app.usecase.ports.out;

import com.labotec.traccar.app.utils.common.repository.GenericCrudService;
import com.labotec.traccar.domain.database.models.Driver;
import com.labotec.traccar.domain.web.dto.create.DriverDTO;
import com.labotec.traccar.domain.web.dto.update.DriverUpdateDTO;

public interface DriverService extends GenericCrudService<Driver , DriverDTO, DriverUpdateDTO> {
}
