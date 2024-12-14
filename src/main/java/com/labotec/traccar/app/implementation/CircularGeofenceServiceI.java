package com.labotec.traccar.app.implementation;

import com.labotec.traccar.app.mapper.model.GeofenceCircularModelMapper;
import com.labotec.traccar.app.ports.input.repository.GeofenceCircularRepository;
import com.labotec.traccar.app.ports.input.repository.UserRepository;
import com.labotec.traccar.app.ports.out.services.GeofencePoligonalService;
import com.labotec.traccar.domain.database.models.CircularGeofence;
import com.labotec.traccar.domain.database.models.User;
import com.labotec.traccar.domain.web.dto.labotec.request.create.CircularGeofenceDTO;
import com.labotec.traccar.domain.web.dto.labotec.request.update.GeofencePoligonalUpdateDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CircularGeofenceServiceI implements GeofencePoligonalService {
    private final GeofenceCircularRepository poligonalRepository;
    private final GeofenceCircularModelMapper poligonalModelMapper;
    private final UserRepository userRepository;

    @Override
    public CircularGeofence create(CircularGeofenceDTO circularGeofenceDTO, Long userId) {
        User user =  userRepository.findByUserId(userId);
        CircularGeofence circularGeofence = poligonalModelMapper.toGeofencePoligonal(circularGeofenceDTO);
        circularGeofence.setUserId(user);
        circularGeofence.setCompanyId(user.getCompanyId());
        return poligonalRepository.create(circularGeofence);
    }

    @Override
    public CircularGeofence findById(Long resourceId, Long userId) {
        return poligonalRepository.findById(resourceId,userId);
    }

    @Override
    public Iterable<CircularGeofence> findAll(Long userId) {
        return poligonalRepository.findAll(userId);
    }

    @Override
    public CircularGeofence update(GeofencePoligonalUpdateDTO geofencePoligonalUpdateDTO, Long resourceId, Long userId) {
        CircularGeofence circularGeofenceFind = poligonalRepository.findById(resourceId,userId);
        circularGeofenceFind.setName(geofencePoligonalUpdateDTO.getName());
        circularGeofenceFind.setDescription(geofencePoligonalUpdateDTO.getDescription());
        circularGeofenceFind.setLongitude(geofencePoligonalUpdateDTO.getLongitude());
        circularGeofenceFind.setLatitude(geofencePoligonalUpdateDTO.getLatitude());
        circularGeofenceFind.setRadius(geofencePoligonalUpdateDTO.getRadius());
        return poligonalRepository.update(circularGeofenceFind);}

    @Override
    public void deleteById(Long resourceId, Long userId) {
        poligonalRepository.deleteById(resourceId,userId);
    }
}
