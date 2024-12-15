package com.labotec.traccar.infra.db.mysql.jpa.labotec.impl;


import com.labotec.traccar.app.ports.input.repository.AlertValidParamsRepository;
import com.labotec.traccar.domain.database.models.AlertValidParams;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.AlertValidParamsEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.AlertValidParamsMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.AlertValidParamsEntityRepositoryJpa;
import com.labotec.traccar.app.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class AlertValidParamsRepositoryImpl implements AlertValidParamsRepository {

    private final AlertValidParamsEntityRepositoryJpa jpaRepository;
    private final AlertValidParamsMapper mapper;
    @Override
    public AlertValidParams findById(Long id) {
        AlertValidParamsEntity entity = jpaRepository.findById(id).orElseThrow(
                () ->   new EntityNotFoundException("La alerta buscada no existe")
        );
        return mapper.toModel(entity);
    }

    @Override
    public List<AlertValidParams> findByDeviceId(Long deviceId) {
        List<AlertValidParamsEntity> alertListByDevice = jpaRepository.findByDeviceId(deviceId);
        return mapper.toModelList(alertListByDevice);
    }

    @Override
    public void save(AlertValidParams alertValidParams) {
        AlertValidParamsEntity entity = mapper.toEntity(alertValidParams);
        jpaRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    // Mapear Entidad -> Dominio
    private AlertValidParams mapToDomain(AlertValidParamsEntity entity) {
        return AlertValidParams.builder()
                .id(entity.getId())
                .typeValidation(entity.getTypeValidation())
                .deviceId(entity.getDeviceId())
                .idValidation(entity.getIdValidation())
                .build();
    }

    // Mapear Dominio -> Entidad
    private AlertValidParamsEntity mapToEntity(AlertValidParams domain) {
        AlertValidParamsEntity entity = new AlertValidParamsEntity();
        entity.setId(domain.getId());
        entity.setTypeValidation(domain.getTypeValidation());
        entity.setDeviceId(domain.getDeviceId());
        entity.setIdValidation(domain.getIdValidation());
        return entity;
    }
}
