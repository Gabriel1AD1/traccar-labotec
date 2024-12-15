package com.labotec.traccar.infra.db.mysql.jpa.labotec.impl;

import com.labotec.traccar.app.ports.input.repository.CompanyRepository;
import com.labotec.traccar.domain.database.models.Company;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.CompanyEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.CompanyMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.CompanyRepositoryJpa;
import com.labotec.traccar.app.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class CompanyRepositoryImpl implements CompanyRepository {

    private final CompanyMapper companyMapper;
    private final CompanyRepositoryJpa companyRepositoryJpa;

    @Override
    public Company create(Company entity) {
        CompanyEntity companyEntity = companyMapper.toEntity(entity);
        CompanyEntity companyEntitySaved = companyRepositoryJpa.save(companyEntity);
        return companyMapper.toModel(companyEntitySaved);
    }

    @Override
    public Company findById(Long resourceId) {
        CompanyEntity companyEntity = companyRepositoryJpa.findById(resourceId).orElseThrow(
                ()-> new EntityNotFoundException("Empresa no encontrada")
        );
        return companyMapper.toModel(companyEntity);
    }

    @Override
    public Optional<Company> findByIdOptional(Long resourceId) {
        CompanyEntity companyEntity = companyRepositoryJpa.findById(resourceId).get();
        return Optional.ofNullable(companyMapper.toModel(companyEntity));
    }

    @Override
    public Iterable<Company> findAll() {
        List<CompanyEntity> companyEntities = companyRepositoryJpa.findAll();
        return companyMapper.toModelList(companyEntities);
    }

    @Override
    public Company update(Company entity) {
        CompanyEntity companyEntity = companyMapper.toEntity(entity);
        CompanyEntity companyEntitySaved = companyRepositoryJpa.save(companyEntity);
        return companyMapper.toModel(companyEntitySaved);
    }

    @Override
    public void deleteById(Long resourceId) {
        companyRepositoryJpa.deleteById(resourceId);
    }

}
