package com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper;

import com.labotec.traccar.domain.database.models.Company;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.CompanyEntity;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    Company toModel(CompanyEntity entity);
    CompanyEntity toEntity(Company model);
    List<Company> toModelList(List<CompanyEntity> entities);
    List<CompanyEntity> toEntityList(List<Company> models);
    Set<Company> toModelSet(Set<CompanyEntity> entities);
    Set<CompanyEntity> toEntitySet(Set<Company> models);
    Map<String, Company> toModelMap(Map<String, CompanyEntity> entityMap);
    Map<String, CompanyEntity> toEntityMap(Map<String, Company> modelMap);
}
