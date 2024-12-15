package com.labotec.traccar.app.ports.out.services;

import com.labotec.traccar.app.utils.common.repository.GenericCrudService;
import com.labotec.traccar.domain.database.models.Route;
import com.labotec.traccar.domain.web.labotec.request.create.RouteCreateDTO;
import com.labotec.traccar.domain.web.labotec.request.update.RouteUpdateDTO;
import com.labotec.traccar.domain.web.labotec.response.ResponseRoute;
import com.labotec.traccar.domain.web.labotec.response.ResponseRouteBusStopInformation;

import java.util.List;

public interface RouteService extends GenericCrudService<Route, RouteCreateDTO, RouteUpdateDTO,Long> {

    List<ResponseRoute> findAllByUserId(Long userId);

    List<ResponseRouteBusStopInformation> findAllByRouteId(Long routeId, Long userId);
}
