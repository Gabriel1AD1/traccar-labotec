package com.labotec.traccar.app.mapper;

import com.labotec.traccar.domain.database.models.Company;
import com.labotec.traccar.domain.web.dto.CompanyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface CompanyModelMapper {

    // Instancia del mapper
    CompanyModelMapper INSTANCE = Mappers.getMapper(CompanyModelMapper.class);

    // Mapeo de Company a CompanyDTO
    CompanyDTO toDto(Company company);

    // Mapeo de CompanyDTO a Company
    Company toDomain(CompanyDTO companyDTO);
}
