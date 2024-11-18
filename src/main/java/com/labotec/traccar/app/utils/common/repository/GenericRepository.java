package com.labotec.traccar.app.utils.common.repository;

import java.util.Optional;

/**
 * Interfaz genérica para la gestión de entidades en el repositorio.
 * Proporciona métodos básicos para crear, leer, actualizar y eliminar (CRUD) entidades de cualquier tipo.
 *
 * @param <MODEL> Tipo de la entidad que manejará el repositorio (por ejemplo, Schedule, User).
 * @param <TYPE> Tipo del identificador único de la entidad (por ejemplo, Long, String).
 */
public interface GenericRepository <MODEL, TYPE> {

    /**
     * Crea una nueva entidad en la base de datos.
     *
     * @param entity la entidad que se va a crear
     * @return la entidad creada con los datos persistidos (incluyendo el ID generado, si es aplicable)
     */
    MODEL create(MODEL entity);

    /**
     * Encuentra una entidad por su ID y el ID del usuario asociado.
     *
     * @param resourceId el ID de la entidad a buscar
     * @param userId el ID del usuario asociado a la entidad
     * @return la entidad encontrada, o lanza un error que es manejado por el Global exception handler.
     */
    MODEL findById(TYPE resourceId, TYPE userId);

    /**
     * Encuentra una entidad por su ID y el ID del usuario asociado, devolviendo un Optional.
     * Esto permite manejar casos donde la entidad no exista de manera más controlada.
     *
     * @param resourceId el ID de la entidad a buscar
     * @param userId el ID del usuario asociado a la entidad
     * @return un Optional con la entidad encontrada, o `Optional.empty()` si no se encuentra
     */
    Optional<MODEL> findByIdOptional(TYPE resourceId, TYPE userId);

    /**
     * Encuentra todas las entidades asociadas a un usuario específico.
     *
     * @param userId el ID del usuario para filtrar las entidades
     * @return un iterable de todas las entidades asociadas al usuario
     */
    Iterable<MODEL> findAll(TYPE userId);

    /**
     * Actualiza los datos de una entidad existente en la base de datos.
     *
     * @param entity la entidad con los datos actualizados
     * @return la entidad actualizada, con los datos persistidos
     */
    MODEL update(MODEL entity);

    /**
     * Elimina una entidad por su ID y el ID del usuario asociado.
     *
     * @param resourceId el ID de la entidad a eliminar
     * @param userId el ID del usuario asociado a la entidad
     */
    void deleteById(TYPE resourceId, TYPE userId);
}
