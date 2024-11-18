package com.labotec.traccar.app.mapper;

import com.labotec.traccar.domain.database.models.BusStop;
import com.labotec.traccar.domain.web.dto.entel.create.BusStopDTO;
import com.labotec.traccar.domain.web.dto.entel.update.BusStopUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BusStopModelMapper {

    // Mapeo de BusStop a BusStopDTO
    BusStopDTO toBusStopDTO(BusStop busStop);

    // Mapeo inverso de BusStopDTO a BusStop, pasando el Company como parámetro
    BusStop toBusStopModel(BusStopDTO busStopDTO);
    BusStop toBusStopModel(BusStopUpdateDTO busStopDTO);
}