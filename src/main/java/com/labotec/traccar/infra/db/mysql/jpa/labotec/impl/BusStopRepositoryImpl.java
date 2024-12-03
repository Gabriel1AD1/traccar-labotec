package com.labotec.traccar.infra.db.mysql.jpa.labotec.impl;

import com.labotec.traccar.app.ports.input.repository.BusStopRepository;
import com.labotec.traccar.domain.database.models.BusStop;
import com.labotec.traccar.domain.web.dto.labotec.response.BusStopResponse;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.BusStopEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.BusStopMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.response.BusStopResponseMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.projection.BusStopProjection;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.BusStopRepositoryJpa;
import com.labotec.traccar.infra.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.labotec.traccar.infra.db.mysql.jpa.labotec.message.BusStopMessage.BUS_STOP_NOT_FOUND_BY_ID;

@AllArgsConstructor
@Repository
public class BusStopRepositoryImpl implements BusStopRepository {
    private final BusStopRepositoryJpa busStopRepositoryJpa;
    private final BusStopMapper busStopMapper;
    private final BusStopResponseMapper busStopResponseMapper;
    @Override
    public BusStop create(BusStop entity) {
        BusStopEntity busStopEntity = busStopMapper.toEntity(entity);
        BusStopEntity busStopEntitySaved = busStopRepositoryJpa.save(busStopEntity);
        return busStopMapper.toModel(busStopEntitySaved);
    }

    @Override
    public BusStop findById(Long resourceId, Long userId) {
        BusStopEntity busStopEntity = busStopRepositoryJpa.findByIdAndUserId(resourceId,userId).orElseThrow(
                () -> new EntityNotFoundException(BUS_STOP_NOT_FOUND_BY_ID + resourceId)
        );
        return busStopMapper.toModel(busStopEntity);
    }

    @Override
    public Optional<BusStop> findByIdOptional(Long resourceId, Long userId) {
        BusStopEntity busStopEntity = busStopRepositoryJpa.findByIdAndUserId(resourceId,userId).get();
        return Optional.of(busStopMapper.toModel(busStopEntity));
    }

    @Override
    public Iterable<BusStop> findAll(Long userId) {
        List<BusStopEntity> entityList = busStopRepositoryJpa.findAllByUserId(userId);
        return busStopMapper.toModelList(entityList);
    }

    @Override
    public BusStop update(BusStop entity) {
        BusStopEntity busStopEntity = busStopMapper.toEntity(entity);
        BusStopEntity busStopEntitySaved = busStopRepositoryJpa.save(busStopEntity);
        return busStopMapper.toModel(busStopEntitySaved);
    }

    @Override
    public void deleteById(Long resourceId, Long userId) {
        busStopRepositoryJpa.deleteByUserIdAndCompanyId(resourceId,userId);
    }

    @Override
    public void validateIdBusStop(Long startBusStopId, Long userId) {
        if (!busStopRepositoryJpa.existsByIdAndUserId(startBusStopId,userId)){
            throw  new EntityNotFoundException("El paradero buscado no existe");
        }
    }

    @Override
    public BusStopResponse findByResourceId(Long busStopId) {
        BusStopProjection busStopProjection = busStopRepositoryJpa.findByResourceId(busStopId).orElseThrow(
                () -> new EntityNotFoundException("No encontrado")
        );
        BusStopResponse busStopResponse = new BusStopResponse();
        busStopResponse.setId(busStopProjection.getId());
        busStopResponse.setLongitude(busStopProjection.getLongitude());
        busStopResponse.setLatitude(busStopProjection.getLatitude());
        busStopResponse.setName(busStopProjection.getName());
        busStopResponse.setStatus(busStopProjection.getStatus());
        busStopResponse.setDescription(busStopProjection.getDescription());
        return busStopResponse;
    }
}
