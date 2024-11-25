package com.labotec.traccar.app.implementation;

import com.labotec.traccar.app.mapper.model.DriverModelMapper;
import com.labotec.traccar.app.ports.input.repository.CompanyRepository;
import com.labotec.traccar.app.ports.input.repository.DriverRepository;
import com.labotec.traccar.app.ports.input.repository.UserRepository;
import com.labotec.traccar.app.ports.out.DriverService;
import com.labotec.traccar.domain.database.models.Driver;
import com.labotec.traccar.domain.database.models.User;
import com.labotec.traccar.domain.web.dto.labotec.request.create.DriverDTO;
import com.labotec.traccar.domain.web.dto.labotec.request.update.DriverUpdateDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor

public class DriverImpl implements DriverService {

    private final DriverRepository driverRepository ;
    private final CompanyRepository companyRepository;
    private final DriverModelMapper driverModelMapper;
    private final UserRepository userRepository;

    @Override
    public Driver create(DriverDTO driverDTO, Long userId) {
        User user = userRepository.findByUserId(userId);
        Driver driver = driverModelMapper.toEntity(driverDTO);
        driver.setUserId(user);
        driver.setCompanyId(user.getCompanyId());
        return driverRepository.create(driver);
    }

    @Override
    public Driver findById(Long resourceId, Long userId) {
        return driverRepository.findById(resourceId,userId);
    }

    @Override
    public Iterable<Driver> findAll(Long userId) {
        return driverRepository.findAll(userId);
    }

    @Override
    public Driver update(DriverUpdateDTO driverUpdateDTO, Long resourceId, Long userId) {
        Driver driver = driverRepository.findById(resourceId,userId);
        driver.setStatus(driverUpdateDTO.getStatus());
        driver.setDocumentNumber(driverUpdateDTO.getDocumentNumber());
        driver.setFirstName(driverUpdateDTO.getFirstName());
        driver.setDocumentType(driverUpdateDTO.getDocumentType());
        return driverRepository.update(driver);
    }

    @Override
    public void deleteById(Long resourceId, Long userId) {
        driverRepository.deleteById(resourceId,userId);
    }
}
