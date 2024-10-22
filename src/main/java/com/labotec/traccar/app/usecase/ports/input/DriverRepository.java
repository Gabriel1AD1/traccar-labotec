package com.labotec.traccar.app.usecase.ports.input;

import com.labotec.traccar.app.utils.common.repository.GenericRepository;
import com.labotec.traccar.domain.database.models.Driver;
import com.labotec.traccar.domain.web.dto.DriverDTO;

public interface DriverRepository extends GenericRepository<Driver, Integer> {
}
