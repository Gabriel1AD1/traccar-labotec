package com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper;
import com.labotec.traccar.domain.database.models.Location;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.LocationEntity;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
public interface LocationMapper {

    // De entidad a modelo
    Location toModel(LocationEntity entity);

    // De modelo a entidad
    LocationEntity toEntity(Location model);

    // Mapear listas
    List<Location> toModelList(List<LocationEntity> entities);
    List<LocationEntity> toEntityList(List<Location> models);

    // Mapear conjuntos
    Set<Location> toModelSet(Set<LocationEntity> entities);
    Set<LocationEntity> toEntitySet(Set<Location> models);

    // Mapear mapas
    Map<String, Location> toModelMap(Map<String, LocationEntity> entityMap);
    Map<String, LocationEntity> toEntityMap(Map<String, Location> modelMap);
}