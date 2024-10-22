package com.labotec.traccar.app.usecase.ports.out;

import com.labotec.traccar.app.utils.common.repository.GenericCrudService;
import com.labotec.traccar.domain.database.models.RouteBusStop;
import com.labotec.traccar.domain.web.dto.RouteBusStopDTO;
import com.labotec.traccar.domain.web.dto.RouteBusUpdateStopDTO;

public interface RouteBusStopService extends GenericCrudService<RouteBusStop , RouteBusStopDTO ,Integer> {

    Iterable<RouteBusStop> findByRoute(Integer routeId);
    Iterable<RouteBusStop> createList(RouteBusStopDTO entityDto);

    Iterable<RouteBusStop> updateToList(RouteBusUpdateStopDTO entityDto);
    }
