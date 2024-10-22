package com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper;

import com.labotec.traccar.domain.database.models.RouteBusStop;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.RouteBusStopEntity;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {RouteMapper.class, BusStopMapper.class})
public interface RouteBusStopMapper {

    // De entidad a modelo
    RouteBusStop toModel(RouteBusStopEntity entity);

    // De modelo a entidad
    RouteBusStopEntity toEntity(RouteBusStop model);

    // Mapear listas
    List<RouteBusStop> toModelList(List<RouteBusStopEntity> entities);
    List<RouteBusStopEntity> toEntityList(List<RouteBusStop> models);

    // Mapear Iterables
    Iterable<RouteBusStop> toModelIterable(Iterable<RouteBusStopEntity> entities);
    Iterable<RouteBusStopEntity> toEntityIterable(Iterable<RouteBusStop> models);

    // Mapear conjuntos
    Set<RouteBusStop> toModelSet(Set<RouteBusStopEntity> entities);
    Set<RouteBusStopEntity> toEntitySet(Set<RouteBusStop> models);

    // Mapear mapas
    Map<String, RouteBusStop> toModelMap(Map<String, RouteBusStopEntity> entityMap);
    Map<String, RouteBusStopEntity> toEntityMap(Map<String, RouteBusStop> modelMap);
}