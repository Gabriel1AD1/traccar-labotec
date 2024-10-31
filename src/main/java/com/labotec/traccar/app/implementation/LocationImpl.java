package com.labotec.traccar.app.implementation;

import com.labotec.traccar.app.mapper.LocationModelMapper;
import com.labotec.traccar.app.usecase.ports.input.repository.CompanyRepository;
import com.labotec.traccar.app.usecase.ports.input.repository.LocationRepository;
import com.labotec.traccar.app.usecase.ports.out.LocationService;
import com.labotec.traccar.domain.database.models.Company;
import com.labotec.traccar.domain.database.models.Location;
import com.labotec.traccar.domain.web.dto.entel.create.LocationDTO;
import com.labotec.traccar.domain.web.dto.entel.update.LocationUpdateDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LocationImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final CompanyRepository companyRepository;
    private final LocationModelMapper locationModelMapper;
    @Override
    public Location create(LocationDTO entityDto) {
        Company company = companyRepository.findById(entityDto.getCompanyId());
        Location location = locationModelMapper.toEntity(entityDto);
        location.setCompany(company);
        return locationRepository.create(location);
    }

    @Override
    public Location findById(Integer integer) {
        return locationRepository.findById(integer);
    }

    @Override
    public Iterable<Location> findAll() {
        return locationRepository.findAll();
    }

    @Override
    public Location update(LocationUpdateDTO entityDto, Integer id) {
        Location location = locationRepository.findById(id);
        Company company = companyRepository.findById(entityDto.getCompanyId());
        location.setCompany(company);
        return locationRepository.update(location);    }

    @Override
    public void deleteById(Integer integer) {
        locationRepository.deleteById(integer);
    }
}
