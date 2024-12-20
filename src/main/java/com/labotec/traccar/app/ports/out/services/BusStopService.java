package com.labotec.traccar.app.ports.out.services;

import com.labotec.traccar.app.utils.common.repository.GenericCrudService;
import com.labotec.traccar.domain.database.models.BusStop;
import com.labotec.traccar.domain.database.models.BusStopUpdateListDTO;
import com.labotec.traccar.domain.web.labotec.request.create.BusStopCreateDTO;
import com.labotec.traccar.domain.web.labotec.request.update.BusStopUpdateDTO;

import java.util.List;

public interface BusStopService
        extends GenericCrudService<BusStop, BusStopCreateDTO, BusStopUpdateDTO,Long> {
    List<Long> createBusStopList(List<BusStopCreateDTO> busStopListCreateDTO, Long userId);
    void updateListBusStop(List<BusStopUpdateListDTO> busStopUpdateListDTO,Long userId);
}
