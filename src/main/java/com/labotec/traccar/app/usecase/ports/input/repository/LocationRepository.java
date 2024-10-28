package com.labotec.traccar.app.usecase.ports.input.repository;

import com.labotec.traccar.app.utils.common.repository.GenericRepository;
import com.labotec.traccar.domain.database.models.Location;

public interface LocationRepository extends GenericRepository<Location , Integer> {
}
