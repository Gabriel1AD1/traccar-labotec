package com.labotec.traccar.app.usecase.ports.out;

import com.labotec.traccar.app.utils.common.repository.GenericCrudService;
import com.labotec.traccar.domain.database.models.Route;
import com.labotec.traccar.domain.web.dto.LastedInformationVehicle;
import com.labotec.traccar.domain.web.dto.create.RouteDTO;
import com.labotec.traccar.domain.web.dto.update.RouteUpdateDTO;

public interface RouteService extends GenericCrudService<Route, RouteDTO , RouteUpdateDTO> {
}
