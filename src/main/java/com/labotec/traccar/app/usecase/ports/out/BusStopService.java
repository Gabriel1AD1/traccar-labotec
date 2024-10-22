package com.labotec.traccar.app.usecase.ports.out;

import com.labotec.traccar.app.utils.common.repository.GenericCrudService;
import com.labotec.traccar.domain.database.models.BusStop;
import com.labotec.traccar.domain.web.dto.BusStopDTO;

public interface BusStopService extends GenericCrudService<BusStop, BusStopDTO,Integer> {
}
