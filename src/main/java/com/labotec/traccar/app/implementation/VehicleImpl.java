package com.labotec.traccar.app.implementation;

import com.labotec.traccar.app.mapper.VehicleModelMapper;
import com.labotec.traccar.app.usecase.ports.input.repository.CompanyRepository;
import com.labotec.traccar.app.usecase.ports.input.repository.UserRepository;
import com.labotec.traccar.app.usecase.ports.input.repository.VehicleRepository;
import com.labotec.traccar.app.usecase.ports.input.repository.VehicleTypeRepository;
import com.labotec.traccar.app.usecase.ports.out.VehicleService;
import com.labotec.traccar.domain.database.models.Company;
import com.labotec.traccar.domain.database.models.User;
import com.labotec.traccar.domain.database.models.Vehicle;
import com.labotec.traccar.domain.database.models.VehicleType;
import com.labotec.traccar.domain.web.dto.entel.create.VehicleDTO;
import com.labotec.traccar.domain.web.dto.entel.update.VehicleUpdateDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor

public class VehicleImpl  implements VehicleService {
    private final VehicleRepository vehicleRepository;
    private final VehicleModelMapper vehicleModelMapper;
    private final CompanyRepository companyRepository;
    private final VehicleTypeRepository vehicleTypeRepository;
    private final UserRepository userRepository;

    @Override
    public Vehicle create(VehicleDTO vehicleDTO, Long userId) {
        User user = userRepository.findByUserId(userId);
        Vehicle vehicleMap = vehicleModelMapper.toVehicleModel(vehicleDTO);
        VehicleType vehicleType = vehicleTypeRepository.findById(vehicleDTO.getTypeVehicleId(),userId);
        vehicleMap.setUserId(user);
        vehicleMap.setCompanyId(user.getCompanyId());
        vehicleMap.setTypeVehicle(vehicleType);
        return vehicleRepository.create(vehicleMap);
    }

    @Override
    public Vehicle findById(Long resourceId, Long userId) {
        return vehicleRepository.findById(resourceId,userId);
    }

    @Override
    public Iterable<Vehicle> findAll(Long userId) {
        return vehicleRepository.findAll(userId);
    }

    @Override
    public Vehicle update(VehicleUpdateDTO vehicleUpdateDTO, Long resourceId, Long userId) {
        Vehicle vehicle = vehicleRepository.findById(resourceId, userId);
        VehicleType vehicleType = vehicleTypeRepository.findById(vehicleUpdateDTO.getTypeVehicleId(),userId);
        vehicle.setBrand(vehicleUpdateDTO.getBrand());
        vehicle.setTypeVehicle(vehicleType);
        vehicle.setStatus(vehicleUpdateDTO.getStatus());
        vehicle.setLicensePlate(vehicleUpdateDTO.getLicensePlate());
        vehicle.setModel(vehicleUpdateDTO.getModel());
        vehicle.setTraccarDeviceId(vehicleUpdateDTO.getTraccarDeviceId());
        return vehicleRepository.update(vehicle);
    }

    @Override
    public void deleteById(Long resourceId, Long userId) {
        vehicleRepository.deleteById(resourceId,userId);

    }
}
