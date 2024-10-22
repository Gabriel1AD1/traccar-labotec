package com.labotec.traccar.app.implemenatition;

import com.labotec.traccar.app.mapper.VehicleModelMapper;
import com.labotec.traccar.app.usecase.ports.input.CompanyRepository;
import com.labotec.traccar.app.usecase.ports.input.VehicleRepository;
import com.labotec.traccar.app.usecase.ports.input.VehicleTypeRepository;
import com.labotec.traccar.app.usecase.ports.out.VehicleService;
import com.labotec.traccar.app.utils.common.repository.GenericCrudService;
import com.labotec.traccar.domain.database.models.Company;
import com.labotec.traccar.domain.database.models.Vehicle;
import com.labotec.traccar.domain.database.models.VehicleType;
import com.labotec.traccar.domain.web.dto.VehicleDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor

public class VehicleImpl  implements VehicleService {
    private final VehicleRepository vehicleRepository;
    private final VehicleModelMapper vehicleModelMapper;
    private final CompanyRepository companyRepository;
    private final VehicleTypeRepository vehicleTypeRepository;
    @Override
    public Vehicle create(VehicleDTO entityDto) {
        Vehicle vehicleMap = vehicleModelMapper.INSTANCE.toVehicleModel(entityDto);
        VehicleType vehicleType = vehicleTypeRepository.findById(entityDto.getTypeVehicleId());
        Company company = companyRepository.findById(entityDto.getCompanyId());
        vehicleMap.setCompany(company);
        vehicleMap.setTypeVehicle(vehicleType);
        return vehicleRepository.create(vehicleMap);
    }

    @Override
    public Vehicle findById(Integer integer) {
        return vehicleRepository.findById(integer);
    }

    @Override
    public Iterable<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    @Override
    public Vehicle update(VehicleDTO entityDto, Integer integer) {
        Vehicle vehicleFind = vehicleRepository.findById(integer);
        Vehicle vehicleMap = vehicleModelMapper.INSTANCE.toVehicleModel(entityDto);
        Company company = companyRepository.findById(entityDto.getCompanyId());
        vehicleMap.setId(vehicleFind.getId());
        vehicleMap.setCompany(company);
        return vehicleRepository.update(vehicleMap);
    }

    @Override
    public void deleteById(Integer integer) {
        vehicleRepository.deleteById(integer);
    }
}
