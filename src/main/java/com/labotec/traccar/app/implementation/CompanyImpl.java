package com.labotec.traccar.app.implementation;

import com.labotec.traccar.app.mapper.CompanyModelMapper;
import com.labotec.traccar.app.usecase.ports.input.repository.CompanyRepository;
import com.labotec.traccar.app.usecase.ports.out.CompanyService;
import com.labotec.traccar.domain.database.models.Company;
import com.labotec.traccar.domain.web.dto.entel.create.CompanyDTO;
import com.labotec.traccar.domain.web.dto.entel.update.CompanyUpdateDTO;
import lombok.AllArgsConstructor;

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
    public Company update(CompanyUpdateDTO entityDto, Integer integer) {
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
