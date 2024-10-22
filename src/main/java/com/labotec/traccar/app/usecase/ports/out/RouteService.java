package com.labotec.traccar.app.usecase.ports.out;

import com.labotec.traccar.app.utils.common.repository.GenericCrudService;
import com.labotec.traccar.domain.database.models.Route;
import com.labotec.traccar.domain.web.dto.RouteDTO;

public interface RouteService extends GenericCrudService<Route, RouteDTO , Integer> {
}
