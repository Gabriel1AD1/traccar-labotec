package com.labotec.traccar.app.implementation;

import com.labotec.traccar.app.mapper.DriverModelMapper;
import com.labotec.traccar.app.usecase.ports.input.repository.CompanyRepository;
import com.labotec.traccar.app.usecase.ports.input.repository.DriverRepository;
import com.labotec.traccar.app.usecase.ports.out.DriverService;
import com.labotec.traccar.domain.database.models.Company;
import com.labotec.traccar.domain.database.models.Driver;
import com.labotec.traccar.domain.web.dto.entel.create.DriverDTO;
import com.labotec.traccar.domain.web.dto.entel.update.DriverUpdateDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor

public class DriverImpl implements DriverService {

    private final DriverRepository driverRepository ;
    private final CompanyRepository companyRepository;
    private final DriverModelMapper driverModelMapper;
    @Override
    public Driver create(DriverDTO entityDto) {
        Company company = companyRepository.findById(entityDto.getCompanyId());
        Driver driver = driverModelMapper.toEntity(entityDto);
        driver.setCompany(company);
        return driverRepository.create(driver);
    }

    @Override
    public Driver findById(Integer integer) {
        return driverRepository.findById(integer);
    }

    @Override
    public Iterable<Driver> findAll() {
        return driverRepository.findAll();
    }

    @Override
    public Driver update(DriverUpdateDTO entityDto, Integer integer) {
        Company company = companyRepository.findById(entityDto.getCompanyId());
        Driver driver = driverModelMapper.toEntity(entityDto);
        driver.setCompany(company);
        return driverRepository.update(driver);    }

    @Override
    public void deleteById(Integer integer) {
        driverRepository.deleteById(integer);
    }
}
