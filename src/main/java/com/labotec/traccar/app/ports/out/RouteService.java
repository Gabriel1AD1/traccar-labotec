package com.labotec.traccar.app.ports.out;

import com.labotec.traccar.app.utils.common.repository.GenericCrudService;
import com.labotec.traccar.domain.database.models.Route;
import com.labotec.traccar.domain.web.dto.labotec.request.create.RouteCreateDTO;
import com.labotec.traccar.domain.web.dto.labotec.request.update.RouteUpdateDTO;

public interface RouteService extends GenericCrudService<Route, RouteCreateDTO, RouteUpdateDTO,Long> {
}
