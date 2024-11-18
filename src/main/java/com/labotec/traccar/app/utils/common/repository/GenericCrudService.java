    package com.labotec.traccar.app.utils.common.repository;

    public interface GenericCrudService<MODELO, CREATE_DTO, UPDATE_DTO,TYPE_DATE> {

        MODELO create(CREATE_DTO createDto , TYPE_DATE userId);

        MODELO findById(TYPE_DATE resourceId , TYPE_DATE userId);

        Iterable<MODELO> findAll(TYPE_DATE userId);

        MODELO update(UPDATE_DTO updateDto, TYPE_DATE resourceId , TYPE_DATE userId);

        void deleteById(TYPE_DATE resourceId ,TYPE_DATE userId);
    }