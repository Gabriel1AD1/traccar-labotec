package com.labotec.traccar.app.usecase.ports.out;

import com.labotec.traccar.app.utils.common.repository.GenericCrudService;
import com.labotec.traccar.domain.database.models.Location;
import com.labotec.traccar.domain.web.dto.create.LocationDTO;
import com.labotec.traccar.domain.web.dto.update.LocationUpdateDTO;

public interface LocationService extends GenericCrudService<Location , LocationDTO , LocationUpdateDTO> {
}
