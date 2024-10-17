package com.labotec.traccar.infra.db.mysql.jpa.mapper;

import com.labotec.traccar.domain.database.models.Company;
import com.labotec.traccar.infra.db.mysql.jpa.entity.CompanyEntity;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    // De entidad a modelo
    Company toModel(CompanyEntity entity);

    // De modelo a entidad
    CompanyEntity toEntity(Company model);

    // Mapear listas
    List<Company> toModelList(List<CompanyEntity> entities);
    List<CompanyEntity> toEntityList(List<Company> models);

    // Mapear conjuntos
    Set<Company> toModelSet(Set<CompanyEntity> entities);
    Set<CompanyEntity> toEntitySet(Set<Company> models);

    // Mapear mapas
    Map<String, Company> toModelMap(Map<String, CompanyEntity> entityMap);
    Map<String, CompanyEntity> toEntityMap(Map<String, Company> modelMap);
}