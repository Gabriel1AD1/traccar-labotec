package com.labotec.traccar.infra.db.mysql.jpa.impl;

import com.labotec.traccar.app.usecase.ports.input.CompanyRepository;
import com.labotec.traccar.domain.database.models.Company;
import com.labotec.traccar.domain.web.dto.CompanyDTO;
import com.labotec.traccar.infra.db.mysql.jpa.entity.CompanyEntity;
import com.labotec.traccar.infra.db.mysql.jpa.mapper.CompanyMapper;
import com.labotec.traccar.infra.db.mysql.jpa.repository.CompanyRepositoryJpa;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.labotec.traccar.infra.db.mysql.jpa.message.ComapanyMessage.COMPANY_NOT_FOUND_BY_ID;

@AllArgsConstructor
@Repository
public class CompanyRepositoryImpl implements CompanyRepository {

    private final CompanyMapper companyMapper;
    private final CompanyRepositoryJpa companyRepositoryJpa;

    @Override
    public Company create(CompanyDTO entity) {
        // Mapeo manual del DTO a la entidad
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setLabNombre(entity.getName());
        companyEntity.setLabRuc(entity.getRuc());
        companyEntity.setLabEstado(entity.getStatus());

        // Guardar la entidad en la base de datos
        CompanyEntity savedCompany = companyRepositoryJpa.save(companyEntity);

        // Convertir la entidad guardada en un modelo y devolverlo
        return companyMapper.toModel(savedCompany);
    }

    @Override
    public Company findById(Integer id) {
        CompanyEntity companyEntity = companyRepositoryJpa.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(COMPANY_NOT_FOUND_BY_ID + id));
        return companyMapper.toModel(companyEntity);
    }

    @Override
    public Optional<Company> findByIdOptional(Integer id) {
        return companyRepositoryJpa.findById(id)
                .map(companyMapper::toModel);
    }

    @Override
    public List<Company> findAll() {
        List<CompanyEntity> companyEntities = companyRepositoryJpa.findAll();
        return companyMapper.toModelList(companyEntities);
    }

    @Override
    public Company update(CompanyDTO entity, Integer id) {
        CompanyEntity companyEntity = companyRepositoryJpa.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(COMPANY_NOT_FOUND_BY_ID + id));

        // Actualización manual de los campos desde el DTO
        companyEntity.setLabNombre(entity.getName());
        companyEntity.setLabRuc(entity.getRuc());
        companyEntity.setLabEstado(entity.getStatus());

        // Guardar los cambios en la base de datos
        CompanyEntity updatedCompany = companyRepositoryJpa.save(companyEntity);

        return companyMapper.toModel(updatedCompany);
    }

    @Override
    public void deleteById(Integer id) {
        companyRepositoryJpa.deleteById(id);
    }
}
