package com.labotec.traccar.app.usecase.ports.out;

import com.labotec.traccar.app.utils.common.repository.GenericCrudService;
import com.labotec.traccar.domain.database.models.OverviewPolyline;
import com.labotec.traccar.domain.web.dto.create.OverviewPolylineDTO;
import com.labotec.traccar.domain.web.dto.update.OverviewPolylineUpdateDTO;

public interface OverviewPolylineService extends GenericCrudService<OverviewPolyline , OverviewPolylineDTO, OverviewPolylineUpdateDTO> {
}
