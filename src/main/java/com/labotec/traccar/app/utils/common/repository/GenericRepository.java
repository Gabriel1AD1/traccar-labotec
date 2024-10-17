package com.labotec.traccar.app.utils.common.repository;

import java.util.Optional;

public interface GenericRepository <MODEL , ID , DTO> {

    MODEL create(DTO entity);

    MODEL findById(ID id);
    Optional<MODEL> findByIdOptional(ID id);

    Iterable<MODEL> findAll();

    MODEL update(DTO entity , ID id);

    void deleteById(ID id);
}