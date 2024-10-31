package com.labotec.traccar.app.implementation;

import com.labotec.traccar.app.mapper.GeofenceCircularModelMapper;
import com.labotec.traccar.app.usecase.ports.input.repository.GeofenceCircularRepository;
import com.labotec.traccar.app.usecase.ports.out.GeofencePoligonalService;
import com.labotec.traccar.domain.database.models.CircularGeofence;
import com.labotec.traccar.domain.web.dto.entel.create.CircularGeofenceDTO;
import com.labotec.traccar.domain.web.dto.entel.update.GeofencePoligonalUpdateDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CircularGeofenceServiceImpl implements GeofencePoligonalService {
    private final GeofenceCircularRepository poligonalRepository;
    private final GeofenceCircularModelMapper poligonalModelMapper;
    @Override
    public CircularGeofence create(CircularGeofenceDTO entityDto) {
        CircularGeofence circularGeofence = poligonalModelMapper.toGeofencePoligonal(entityDto);
        return poligonalRepository.create(circularGeofence);
    }

    @Override
    public CircularGeofence findById(Integer integer) {
        return poligonalRepository.findById(integer);
    }

    @Override
    public Iterable<CircularGeofence> findAll() {
        return poligonalRepository.findAll();
    }

    @Override
    public CircularGeofence update(GeofencePoligonalUpdateDTO entityDto, Integer id) {
        CircularGeofence circularGeofenceFind = poligonalRepository.findById(id);
        circularGeofenceFind.setName(entityDto.getName());
        circularGeofenceFind.setDescription(entityDto.getDescription());
        circularGeofenceFind.setLongitude(entityDto.getLongitude());
        circularGeofenceFind.setLatitude(entityDto.getLatitude());
        circularGeofenceFind.setRadius(entityDto.getRadius());
        return poligonalRepository.update(circularGeofenceFind);
    }

    @Override
    public void deleteById(Integer integer) {
        poligonalRepository.deleteById(integer);
    }

}
