package com.labotec.traccar.app.implementation;

import com.labotec.traccar.app.mapper.model.LocationModelMapper;
import com.labotec.traccar.app.ports.input.repository.BusStopRepository;
import com.labotec.traccar.app.ports.input.repository.CompanyRepository;
import com.labotec.traccar.app.ports.input.repository.LocationRepository;
import com.labotec.traccar.app.ports.input.repository.UserRepository;
import com.labotec.traccar.app.ports.out.LocationService;
import com.labotec.traccar.domain.database.models.BusStop;
import com.labotec.traccar.domain.database.models.Location;
import com.labotec.traccar.domain.database.models.User;
import com.labotec.traccar.domain.enums.STATE;
import com.labotec.traccar.domain.web.dto.labotec.request.create.LocationDTO;
import com.labotec.traccar.domain.web.dto.labotec.request.update.LocationUpdateDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LocationServiceI implements LocationService {
    private final BusStopRepository busStopRepository;
    private final LocationRepository locationRepository;
    private final CompanyRepository companyRepository;
    private final LocationModelMapper locationModelMapper;
    private final UserRepository userRepository;

    @Override
    public Location create(LocationDTO locationDTO, Long userId) {
        User user = userRepository.findByUserId(userId);
        Location location = locationModelMapper.toEntity(locationDTO);
        BusStop busStop = busStopRepository.findById(locationDTO.getBusStopAssociateId(), userId);
        location.setUserId(user);
        location.setCompanyId(user.getCompanyId());
        location.setBusStopAssociate(busStop);
        location.setState(STATE.ACTIVO);
        location.setDescription(locationDTO.getDescription());
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
