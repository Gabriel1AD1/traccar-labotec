package com.labotec.traccar.app.mapper.model;

import com.labotec.traccar.domain.database.models.BusStop;
import com.labotec.traccar.domain.database.models.BusStopUpdateListDTO;
import com.labotec.traccar.domain.web.labotec.request.create.BusStopCreateDTO;
import com.labotec.traccar.domain.web.labotec.request.update.BusStopUpdateDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BusStopModelMapper {

    // Mapeo de BusStop a BusStopCreateDTO
    BusStopCreateDTO toBusStopDTO(BusStop busStop);

    // Mapeo inverso de BusStopCreateDTO a BusStop, pasando el Company como parámetro
    BusStop toBusStopModel(BusStopCreateDTO busStopCreateDTO);
    BusStop toBusStopModel(BusStopUpdateDTO busStopDTO);

    BusStop busStopModelToListBusStop(BusStopUpdateListDTO busStopCreateDTO);
}