package com.labotec.traccar.app.implementation;

import com.labotec.traccar.app.mapper.LocationModelMapper;
import com.labotec.traccar.app.usecase.ports.input.repository.CompanyRepository;
import com.labotec.traccar.app.usecase.ports.input.repository.LocationRepository;
import com.labotec.traccar.app.usecase.ports.input.repository.UserRepository;
import com.labotec.traccar.app.usecase.ports.out.LocationService;
import com.labotec.traccar.domain.database.models.Company;
import com.labotec.traccar.domain.database.models.Location;
import com.labotec.traccar.domain.database.models.User;
import com.labotec.traccar.domain.web.dto.entel.create.LocationDTO;
import com.labotec.traccar.domain.web.dto.entel.update.LocationUpdateDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LocationImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final CompanyRepository companyRepository;
    private final LocationModelMapper locationModelMapper;
    private final UserRepository userRepository;

    @Override
    public Location create(LocationDTO locationDTO, Long userId) {
        User user = userRepository.findByUserId(userId);
        Location location = locationModelMapper.toEntity(locationDTO);
        location.setUserId(user);
        location.setCompanyId(user.getCompanyId());
        return locationRepository.create(location);

    }

    @Override
    public Location findById(Long resourceId, Long userId) {
        return locationRepository.findById(resourceId,userId);
    }

    @Override
    public Iterable<Location> findAll(Long userId) {
        return locationRepository.findAll(userId);
    }

    @Override
    public Location update(LocationUpdateDTO locationUpdateDTO, Long resourceId, Long userId) {
        Location location = locationRepository.findById(resourceId,userId);
        location.setLatitude(locationUpdateDTO.getLatitude());
        location.setLongitude(locationUpdateDTO.getLongitude());
        location.setName(locationUpdateDTO.getName());
        location.setRadius(locationUpdateDTO.getRadius());
        return locationRepository.update(location);
    }

    @Override
    public void deleteById(Long resourceId, Long userId) {
        locationRepository.deleteById(resourceId,userId);
    }
}
