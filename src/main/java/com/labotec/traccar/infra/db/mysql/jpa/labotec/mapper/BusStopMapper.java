package com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper;

import com.labotec.traccar.domain.database.models.BusStop;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.BusStopEntity;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
public interface BusStopMapper {

    BusStop toModel(BusStopEntity entity);

    BusStopEntity toEntity(BusStop model);

    List<BusStop> toModelList(List<BusStopEntity> entities);
    List<BusStopEntity> toEntityList(List<BusStop> models);

    Set<BusStop> toModelSet(Set<BusStopEntity> entities);
    Set<BusStopEntity> toEntitySet(Set<BusStop> models);

    Map<String, BusStop> toModelMap(Map<String, BusStopEntity> entityMap);
    Map<String, BusStopEntity> toEntityMap(Map<String, BusStop> modelMap);
}
