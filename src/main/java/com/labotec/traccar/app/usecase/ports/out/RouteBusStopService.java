package com.labotec.traccar.app.usecase.ports.out;

import com.labotec.traccar.app.utils.common.repository.GenericCrudService;
import com.labotec.traccar.domain.database.models.RouteBusStop;
import com.labotec.traccar.domain.web.dto.create.RouteBusStopDTO;
import com.labotec.traccar.domain.web.dto.create.RouteBusStopIterableDTO;
import com.labotec.traccar.domain.web.dto.update.RouteBusStopUpdateDTO;
import com.labotec.traccar.domain.web.dto.update.RouteBusUpdateStopIterableDTO;

public interface RouteBusStopService extends GenericCrudService<RouteBusStop , RouteBusStopDTO , RouteBusStopUpdateDTO> {

    Iterable<RouteBusStop> findByRoute(Integer routeId);
    Iterable<RouteBusStop> createList(RouteBusStopIterableDTO entityDto);

    Iterable<RouteBusStop> updateToList(RouteBusUpdateStopIterableDTO entityDto);
    }
