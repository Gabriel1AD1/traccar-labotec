package com.labotec.traccar.app.usecase.ports.out;

import com.labotec.traccar.app.utils.common.repository.GenericCrudService;
import com.labotec.traccar.domain.database.models.BusStop;
import com.labotec.traccar.domain.web.dto.entel.create.BusStopDTO;
import com.labotec.traccar.domain.web.dto.entel.update.BusStopUpdateDTO;

public interface BusStopService extends GenericCrudService<BusStop, BusStopDTO , BusStopUpdateDTO,Long> {
}
