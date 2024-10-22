package com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper;

import com.labotec.traccar.domain.database.models.Driver;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.DriverEntity;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
public interface DriverMapper {

    // De entidad a modelo
    Driver toModel(DriverEntity entity);

    // De modelo a entidad
    DriverEntity toEntity(Driver model);

    // Mapear listas
    List<Driver> toModelList(List<DriverEntity> entities);
    List<DriverEntity> toEntityList(List<Driver> models);

    // Mapear conjuntos
    Set<Driver> toModelSet(Set<DriverEntity> entities);
    Set<DriverEntity> toEntitySet(Set<Driver> models);

    // Mapear mapas
    Map<String, Driver> toModelMap(Map<String, DriverEntity> entityMap);
    Map<String, DriverEntity> toEntityMap(Map<String, Driver> modelMap);
}