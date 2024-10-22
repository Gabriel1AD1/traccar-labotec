    package com.labotec.traccar.app.utils.common.repository;

    public interface GenericCrudService<MODELO, DTO,ID> {

        MODELO create(DTO entityDto);

        MODELO findById(ID id);

        Iterable<MODELO> findAll();

        MODELO update(DTO entityDto , ID id);

        void deleteById(ID id);
    }