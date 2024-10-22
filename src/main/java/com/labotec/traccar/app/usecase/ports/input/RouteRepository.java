package com.labotec.traccar.app.usecase.ports.input;

import com.labotec.traccar.app.utils.common.repository.GenericRepository;
import com.labotec.traccar.domain.database.models.Route;
import com.labotec.traccar.domain.web.dto.RouteDTO;

public interface RouteRepository extends GenericRepository<Route,  Integer> {

}
