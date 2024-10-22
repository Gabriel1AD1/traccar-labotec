package com.labotec.traccar.app.utils.common.repository;

import java.util.Optional;

public interface GenericRepository <MODEL , ID> {

    MODEL create(MODEL entity);

    MODEL findById(ID id);
    Optional<MODEL> findByIdOptional(ID id);

    Iterable<MODEL> findAll();

    MODEL update(MODEL entity);

    void deleteById(ID id);
}