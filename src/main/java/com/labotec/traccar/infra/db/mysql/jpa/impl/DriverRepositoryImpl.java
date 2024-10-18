package com.labotec.traccar.infra.db.mysql.jpa.impl;

import com.labotec.traccar.app.usecase.ports.input.DriverRepository;
import com.labotec.traccar.domain.database.models.Driver;
import com.labotec.traccar.domain.web.dto.DriverDTO;
import com.labotec.traccar.infra.db.mysql.jpa.entity.CompanyEntity;
import com.labotec.traccar.infra.db.mysql.jpa.entity.DriverEntity;
import com.labotec.traccar.infra.db.mysql.jpa.mapper.DriverMapper;
import com.labotec.traccar.infra.db.mysql.jpa.repository.CompanyRepositoryJpa;
import com.labotec.traccar.infra.db.mysql.jpa.repository.DriverRepositoryJpa;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.labotec.traccar.infra.db.mysql.jpa.message.ComapanyMessage.COMPANY_NOT_FOUND_BY_ID;
import static com.labotec.traccar.infra.db.mysql.jpa.message.DriverMessage.DRIVER_NOT_FOUND_BY_ID;

@AllArgsConstructor
@Repository
public class DriverRepositoryImpl implements DriverRepository {

    private final DriverRepositoryJpa driverRepositoryJpa;
    private final CompanyRepositoryJpa companyRepositoryJpa;
    private final DriverMapper driverMapper;

    @Override
    public Driver create(DriverDTO entity) {
        // Buscar la compañía asociada
        CompanyEntity company = companyRepositoryJpa.findById(entity.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException(COMPANY_NOT_FOUND_BY_ID + entity.getCompanyId()));

        // Crear una entidad DriverEntity manualmente a partir del DTO
        DriverEntity driverEntity = new DriverEntity();
        driverEntity.setFirstName(entity.getFirstName());
        driverEntity.setDocumentType(entity.getDocumentType());
        driverEntity.setDocumentNumber(entity.getDocumentNumber());
        driverEntity.setStatus(entity.getStatus());
        driverEntity.setCompany(company); // Asignar la compañía asociada

        // Guardar la entidad en la base de datos
        DriverEntity savedDriver = driverRepositoryJpa.save(driverEntity);

        // Devolver el modelo de dominio Driver usando el DriverMapper
        return driverMapper.toModel(savedDriver);
    }

    @Override
    public Driver findById(Integer id) {
        DriverEntity driverEntity = driverRepositoryJpa.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(DRIVER_NOT_FOUND_BY_ID + id));
        return driverMapper.toModel(driverEntity);
    }

    @Override
    public Optional<Driver> findByIdOptional(Integer id) {
        return driverRepositoryJpa.findById(id)
                .map(driverMapper::toModel); // Usar el mapper para convertir de entidad a modelo
    }

    @Override
    public List<Driver> findAll() {
        List<DriverEntity> driverEntities = driverRepositoryJpa.findAll();
        return driverMapper.toModelList(driverEntities); // Usar el mapper para convertir la lista de entidades
    }

    @Override
    public Driver update(DriverDTO entity, Integer id) {
        // Buscar la entidad DriverEntity por su ID
        DriverEntity driverEntity = driverRepositoryJpa.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(DRIVER_NOT_FOUND_BY_ID + id));

        // Actualizar manualmente los campos con los valores del DTO
        driverEntity.setFirstName(entity.getFirstName());
        driverEntity.setDocumentType(entity.getDocumentType());
        driverEntity.setDocumentNumber(entity.getDocumentNumber());
        driverEntity.setStatus(entity.getStatus());

        // Buscar la compañía asociada y asignarla
        CompanyEntity company = companyRepositoryJpa.findById(entity.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException(COMPANY_NOT_FOUND_BY_ID + entity.getCompanyId()));
        driverEntity.setCompany(company);

        // Guardar la entidad actualizada en la base de datos
        DriverEntity updatedDriver = driverRepositoryJpa.save(driverEntity);

        // Devolver el modelo de dominio actualizado usando el mapper
        return driverMapper.toModel(updatedDriver);
    }

    @Override
    public void deleteById(Integer id) {
        driverRepositoryJpa.deleteById(id);
    }
}
