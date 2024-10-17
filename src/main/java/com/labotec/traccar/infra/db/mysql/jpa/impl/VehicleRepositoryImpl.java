package com.labotec.traccar.infra.db.mysql.jpa.impl;

import com.labotec.traccar.app.usecase.ports.input.VehicleRepository;
import com.labotec.traccar.domain.database.models.Vehicle;
import com.labotec.traccar.domain.web.dto.VehicleDTO;
import com.labotec.traccar.infra.db.mysql.jpa.entity.CompanyEntity;
import com.labotec.traccar.infra.db.mysql.jpa.entity.VehicleEntity;
import com.labotec.traccar.infra.db.mysql.jpa.mapper.VehicleMapper;
import com.labotec.traccar.infra.db.mysql.jpa.repository.CompanyRepositoryJpa;
import com.labotec.traccar.infra.db.mysql.jpa.repository.VehicleRepositoryJpa;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.labotec.traccar.infra.db.mysql.jpa.message.ComapanyMessage.COMPANY_NOT_FOUND_BY_ID;
import static com.labotec.traccar.infra.db.mysql.jpa.message.VehicleMessage.VEHICLE_NOT_FOUND_BY_ID;

@AllArgsConstructor
@Repository
public class



VehicleRepositoryImpl implements VehicleRepository {

    private final VehicleRepositoryJpa vehicleRepositoryJpa;
    private final CompanyRepositoryJpa companyRepositoryJpa;
    private final VehicleMapper vehicleMapper;

    @Override
    public Vehicle create(VehicleDTO entity) {
        // Buscar la compañía asociada
        CompanyEntity company = companyRepositoryJpa.findById(entity.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException(COMPANY_NOT_FOUND_BY_ID + entity.getCompanyId()));

        // Crear una entidad VehicleEntity manualmente a partir del DTO
        VehicleEntity vehicleEntity = new VehicleEntity();
        vehicleEntity.setLabNumPlaca(entity.getLicensePlate());
        vehicleEntity.setLabEstado(entity.getStatus());
        vehicleEntity.setLabPadron(entity.getRegisterNumber());
        vehicleEntity.setLabMarca(entity.getBrand());
        vehicleEntity.setLabModelo(entity.getModel());
        vehicleEntity.setLabIdCompanyEntity(company);

        // Guardar la entidad en la base de datos
        VehicleEntity savedVehicle = vehicleRepositoryJpa.save(vehicleEntity);

        // Devolver el modelo de dominio Vehicle usando el VehicleMapper
        return vehicleMapper.toModel(savedVehicle);
    }

    @Override
    public Vehicle findById(Integer id) {
        VehicleEntity vehicleEntity = vehicleRepositoryJpa.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(VEHICLE_NOT_FOUND_BY_ID + id));
        return vehicleMapper.toModel(vehicleEntity);
    }

    @Override
    public Optional<Vehicle> findByIdOptional(Integer id) {
        return vehicleRepositoryJpa.findById(id)
                .map(vehicleMapper::toModel); // Usar el mapper para convertir de entidad a modelo
    }

    @Override
    public List<Vehicle> findAll() {
        List<VehicleEntity> vehicleEntities = vehicleRepositoryJpa.findAll();
        return vehicleMapper.toModelList(vehicleEntities); // Usar el mapper para convertir la lista de entidades
    }

    @Override
    public Vehicle update(VehicleDTO entity, Integer id) {
        // Buscar la entidad VehicleEntity por su ID
        VehicleEntity vehicleEntity = vehicleRepositoryJpa.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(VEHICLE_NOT_FOUND_BY_ID + id));

        // Buscar la compañía asociada
        CompanyEntity company = companyRepositoryJpa.findById(entity.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException(COMPANY_NOT_FOUND_BY_ID + entity.getCompanyId()));

        // Actualizar manualmente los campos con los valores del DTO
        vehicleEntity.setLabNumPlaca(entity.getLicensePlate());
        vehicleEntity.setLabEstado(entity.getStatus());
        vehicleEntity.setLabPadron(entity.getRegisterNumber());
        vehicleEntity.setLabMarca(entity.getBrand());
        vehicleEntity.setLabModelo(entity.getModel());
        vehicleEntity.setLabIdCompanyEntity(company);

        // Guardar la entidad actualizada en la base de datos
        VehicleEntity updatedVehicle = vehicleRepositoryJpa.save(vehicleEntity);

        // Devolver el modelo de dominio actualizado usando el mapper
        return vehicleMapper.toModel(updatedVehicle);
    }

    @Override
    public void deleteById(Integer id) {
        vehicleRepositoryJpa.deleteById(id);
    }
}
