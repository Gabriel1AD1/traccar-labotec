package com.labotec.traccar.app.implementation;

import com.labotec.traccar.app.mapper.model.VehicleModelMapper;
import com.labotec.traccar.app.ports.input.repository.*;
import com.labotec.traccar.app.ports.out.VehicleService;
import com.labotec.traccar.domain.database.models.User;
import com.labotec.traccar.domain.database.models.Vehicle;
import com.labotec.traccar.domain.database.models.VehicleType;
import com.labotec.traccar.domain.web.dto.labotec.request.create.VehicleDTO;
import com.labotec.traccar.domain.web.dto.labotec.request.update.VehicleUpdateDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor

public class VehicleServiceI implements VehicleService {
    private final VehicleRepository vehicleRepository;
    private final VehicleModelMapper vehicleModelMapper;
    private final CompanyRepository companyRepository;
    private final VehicleTypeRepository vehicleTypeRepository;
    private final UserRepository userRepository;
    private final DeviceTraccarRepository deviceTraccarRepository;
    @Override
    public Vehicle create(VehicleDTO vehicleDTO, Long userId) {
        User user = userRepository.findByUserId(userId);
        Vehicle vehicleMap = vehicleModelMapper.toVehicleModel(vehicleDTO);
        VehicleType vehicleType = vehicleTypeRepository.findById(vehicleDTO.getTypeVehicleId(),userId);
        vehicleMap.setTraccarDeviceId(deviceTraccarRepository.createDevice(vehicleDTO.getImei(),vehicleDTO.getNameDevice()));
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
        return vehicleRepository.update(vehicle);
    }

    @Override
    public void deleteById(Long resourceId, Long userId) {
        vehicleRepository.deleteById(resourceId,userId);

    }
}
