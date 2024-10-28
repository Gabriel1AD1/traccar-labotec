    package com.labotec.traccar.app.utils.common.repository;

    public interface GenericCrudService<MODELO, CREATE_DTO, UPDATE_DTO> {

        MODELO create(CREATE_DTO createDto);

        MODELO findById(Integer id);

        Iterable<MODELO> findAll();

        MODELO update(UPDATE_DTO updateDto, Integer id);

        void deleteById(Integer id);
    }