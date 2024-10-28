package com.labotec.traccar.infra.web.controller.rest.constant;

public class ApiDocumentationConstants {

    // Endpoints de Schedule (Programación)

    public static final String CREATE_SCHEDULE_SUMMARY = "Crear una nueva programación";
    public static final String CREATE_SCHEDULE_DESCRIPTION = "Este endpoint permite crear una nueva programación para un vehículo.";

    public static final String FIND_SCHEDULE_BY_ID_SUMMARY = "Obtener programación por ID";
    public static final String FIND_SCHEDULE_BY_ID_DESCRIPTION = "Devuelve los detalles de una programación específica según su ID.";

    public static final String FIND_ALL_SCHEDULES_SUMMARY = "Obtener todas las programaciones";
    public static final String FIND_ALL_SCHEDULES_DESCRIPTION = "Devuelve una lista con todas las programaciones registradas.";

    public static final String UPDATE_SCHEDULE_SUMMARY = "Actualizar una programación";
    public static final String UPDATE_SCHEDULE_DESCRIPTION = "Permite actualizar los datos de una programación existente, como la fecha de salida y llegada.";

    public static final String DELETE_SCHEDULE_SUMMARY = "Eliminar una programación";
    public static final String DELETE_SCHEDULE_DESCRIPTION = "Este endpoint permite eliminar una programación específica basada en su ID.";

    public static final String UPDATE_STATUS_SUMMARY = "Actualizar estado de la programación";
    public static final String UPDATE_STATUS_DESCRIPTION = "Permite actualizar el estado de una programación existente, asignándole un estado nuevo.";

    public static final String GET_SCHEDULES_BY_DATE_RANGE_SUMMARY = "Obtener programaciones por rango de fechas";
    public static final String GET_SCHEDULES_BY_DATE_RANGE_DESCRIPTION = "Devuelve las programaciones que tienen fechas de salida y llegada dentro de un rango específico.";

    public static final String UPDATE_ESTIMATED_DEPARTURE_SUMMARY = "Actualizar hora de salida estimada";
    public static final String UPDATE_ESTIMATED_DEPARTURE_DESCRIPTION = "Permite actualizar la hora estimada de salida para una programación específica.";

    public static final String UPDATE_ESTIMATED_ARRIVAL_SUMMARY = "Actualizar hora de llegada estimada";
    public static final String UPDATE_ESTIMATED_ARRIVAL_DESCRIPTION = "Permite actualizar la hora estimada de llegada para una programación específica.";

    // Mensajes de respuesta comunes
    public static final String RESPONSE_200 = "Operación exitosa";
    public static final String RESPONSE_404 = "Recurso no encontrado";
}
