package com.labotec.traccar.app.services;

import com.labotec.traccar.app.usecase.ports.input.CompanyRepository;
import com.labotec.traccar.app.usecase.ports.out.GenericCrudService;
import com.labotec.traccar.domain.database.models.Company;
import com.labotec.traccar.domain.web.dto.CompanyDTO;
import com.labotec.traccar.infra.db.mysql.jpa.mapper.CompanyMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CompanyImpl implements GenericCrudService<Company,CompanyDTO , Integer> {

    private final CompanyRepository companyRepository;
    @Override
    public Company create(CompanyDTO entityDto) {
        return companyRepository.create(entityDto);
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
        return companyRepository.update(entityDto,integer);
    }

    @Override
    public void deleteById(Integer integer) {
        companyRepository.deleteById(integer);
    }
}
