package com.labotec.traccar.infra.db.mysql.jpa.impl;

import com.labotec.traccar.app.usecase.ports.input.BusStopRepository;
import com.labotec.traccar.domain.database.models.BusStop;
import com.labotec.traccar.domain.web.dto.BusStopDTO;
import com.labotec.traccar.infra.db.mysql.jpa.entity.BusStopEntity;
import com.labotec.traccar.infra.db.mysql.jpa.entity.CompanyEntity;
import com.labotec.traccar.infra.db.mysql.jpa.mapper.BusStopMapper;
import com.labotec.traccar.infra.db.mysql.jpa.repository.BusStopRepositoryJpa;
import com.labotec.traccar.infra.db.mysql.jpa.repository.CompanyRepositoryJpa;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.labotec.traccar.infra.db.mysql.jpa.message.BusStopMessage.BUS_STOP_NOT_FOUND_BY_ID;
import static com.labotec.traccar.infra.db.mysql.jpa.message.ComapanyMessage.COMPANY_NOT_FOUND_BY_ID;

@AllArgsConstructor
@Repository
public class BusStopRepositoryImpl implements BusStopRepository {
    private final BusStopRepositoryJpa busStopRepositoryJpa;
    private final CompanyRepositoryJpa companyRepositoryJpa;
    private final BusStopMapper busStopMapper;
    @Override
    public BusStop create(BusStopDTO entity) {
        BusStopEntity busStopEntity = new BusStopEntity();
        busStopEntity.setLatitude(entity.getLatitude());
        busStopEntity.setLongitude(entity.getLongitude());
        busStopEntity.setStatus(entity.getStatus());
        busStopEntity.setName(entity.getName());
        CompanyEntity company = companyRepositoryJpa.findById(entity.getCompanyId()).orElseThrow(
                () -> new EntityNotFoundException("Company not found by id: " + entity.getCompanyId())
        );
        busStopEntity.setCompany(company);
        BusStopEntity busStop = busStopRepositoryJpa.save(busStopEntity);

        return busStopMapper.toModel(busStop) ;
    }

    @Override
    public BusStop findById(Integer aLong) {
        BusStopEntity busStopEntity = busStopRepositoryJpa.findById(aLong).orElseThrow(
                () -> new EntityNotFoundException(BUS_STOP_NOT_FOUND_BY_ID + aLong)
        );
        return busStopMapper.toModel(busStopEntity);
    }

    @Override
    public Optional<BusStop> findByIdOptional(Integer aLong) {
        BusStopEntity busStopEntity = busStopRepositoryJpa.findById(aLong).orElseThrow(
                () -> new EntityNotFoundException(BUS_STOP_NOT_FOUND_BY_ID + aLong)
        );
        BusStop busStop = busStopMapper.toModel(busStopEntity);
        return Optional.of(busStop) ;
    }

    @Override
    public Iterable<BusStop> findAll() {
        List<BusStopEntity> busStopEntityList = busStopRepositoryJpa.findAll();

        return busStopMapper.toModelList(busStopEntityList) ;
    }

    @Override
    public BusStop update(BusStopDTO entity, Integer aLong) {
        BusStopEntity busStopEntity = busStopRepositoryJpa.findById(aLong).orElseThrow(
                () -> new EntityNotFoundException(BUS_STOP_NOT_FOUND_BY_ID + aLong)
        );
        busStopEntity.setLatitude(entity.getLatitude());
        busStopEntity.setLongitude(entity.getLongitude());
        busStopEntity.setStatus(entity.getStatus());
        busStopEntity.setName(entity.getName());
        CompanyEntity company = companyRepositoryJpa.findById(entity.getCompanyId()).orElseThrow(
                () -> new EntityNotFoundException(COMPANY_NOT_FOUND_BY_ID + entity.getCompanyId())
        );
        busStopEntity.setCompany(company);
        BusStopEntity busStop = busStopRepositoryJpa.save(busStopEntity);

        return  busStopMapper.toModel(busStop);
    }

    @Override
    public void deleteById(Integer aLong) {
        busStopRepositoryJpa.deleteById(aLong);
    }
}
