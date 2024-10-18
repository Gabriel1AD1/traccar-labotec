package com.labotec.traccar.infra.db.mysql.jpa.mapper;

import com.labotec.traccar.domain.database.models.BusStop;
import com.labotec.traccar.infra.db.mysql.jpa.entity.BusStopEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
public interface BusStopMapper {

    // De entidad a modelo
    BusStop toModel(BusStopEntity entity);

    // De modelo a entidad
    BusStopEntity toEntity(BusStop model);

    // Mapear listas
    List<BusStop> toModelList(List<BusStopEntity> entities);
    List<BusStopEntity> toEntityList(List<BusStop> models);

    // Mapear conjuntos
    Set<BusStop> toModelSet(Set<BusStopEntity> entities);
    Set<BusStopEntity> toEntitySet(Set<BusStop> models);

    // Mapear mapas
    Map<String, BusStop> toModelMap(Map<String, BusStopEntity> entityMap);
    Map<String, BusStopEntity> toEntityMap(Map<String, BusStop> modelMap);
}