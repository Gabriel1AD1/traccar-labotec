package com.labotec.traccar.app.implemenatition;

import com.labotec.traccar.app.mapper.CompanyModelMapper;
import com.labotec.traccar.app.usecase.ports.input.CompanyRepository;
import com.labotec.traccar.app.usecase.ports.out.CompanyService;
import com.labotec.traccar.domain.database.models.Company;
import com.labotec.traccar.domain.web.dto.CompanyDTO;
import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;

@AllArgsConstructor
public class CompanyImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyModelMapper companyModelMapper;
    @Override
    public Company create(CompanyDTO entityDto) {
        Company company = companyModelMapper.INSTANCE.toDomain(entityDto);
        return companyRepository.create(company);
    }

    @Override
    public Company findById(Integer integer) {
        return companyRepository.findById(integer);
    }

    @Override
    public Iterable<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public Company update(CompanyDTO entityDto, Integer integer) {
        Company companyFind  = companyRepository.findById(integer);
        Company company = companyModelMapper.INSTANCE.toDomain(entityDto);
        company.setId(company.getId());
        return companyRepository.update(companyFind);
    }

    @Override
    public void deleteById(Integer integer) {
        companyRepository.deleteById(integer);
    }
}
