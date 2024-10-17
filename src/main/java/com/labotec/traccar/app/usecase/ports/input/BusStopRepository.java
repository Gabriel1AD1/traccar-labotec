package com.labotec.traccar.app.usecase.ports.input;

import com.labotec.traccar.app.utils.common.repository.GenericRepository;
import com.labotec.traccar.domain.database.models.BusStop;
import com.labotec.traccar.domain.web.dto.BusStopDTO;

public interface BusStopRepository extends GenericRepository<BusStop, Integer , BusStopDTO> {

}
