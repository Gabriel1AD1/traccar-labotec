package com.labotec.traccar.app.ports.out.services;

import com.labotec.traccar.app.utils.common.repository.GenericCrudService;
import com.labotec.traccar.domain.database.models.Location;
import com.labotec.traccar.domain.web.dto.labotec.request.create.LocationDTO;
import com.labotec.traccar.domain.web.dto.labotec.request.update.LocationUpdateDTO;

public interface LocationService extends GenericCrudService<Location , LocationDTO , LocationUpdateDTO , Long> {
}
