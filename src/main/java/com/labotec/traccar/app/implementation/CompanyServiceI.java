package com.labotec.traccar.app.implementation;

import com.labotec.traccar.app.mapper.model.CompanyModelMapper;
import com.labotec.traccar.app.ports.input.repository.CompanyRepository;
import com.labotec.traccar.app.ports.out.services.CompanyService;
import com.labotec.traccar.domain.database.models.Company;
import com.labotec.traccar.domain.web.dto.labotec.request.create.CompanyDTO;
import com.labotec.traccar.domain.web.dto.labotec.request.update.CompanyUpdateDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CompanyServiceI implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyModelMapper companyModelMapper;


    public Company create(CompanyDTO companyDTO) {
        Company company = companyModelMapper.toDomain(companyDTO);
        return companyRepository.create(company);
    }

    @Override
    public void deleteCompanyById(Long companyId) {
        companyRepository.deleteById(companyId);
    }

    public Company findById(Long resourceId) {
        return companyRepository.findById(resourceId);
    }


    public Iterable<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company update(CompanyUpdateDTO companyUpdateDTO, Long resourceId) {
        Company company = companyRepository.findById(resourceId);
        company.setCompanyId(company.getCompanyId());
        return company;
    }

    public void deleteById(Long resourceId) {
        companyRepository.deleteById(resourceId);
    }
}
