package com.labotec.traccar.infra.db.mysql.jpa.labotec.entity;

public enum RouteAssignmentRole {
    CONDUCTOR_PRINCIPAL,       // El conductor principal de la ruta
    CONDUCTOR_SUPLENTE,        // Conductor de respaldo en caso de emergencia
    AUXILIAR_DE_RUTA,          // Asistente que apoya en tareas logísticas
    COBRADOR,                  // Responsable de la recolección de tarifas
    INSPECTOR_DE_RUTA,         // Verifica el cumplimiento del itinerario
    GUIA_DE_TURISMO,           // Acompaña en rutas turísticas
    SUPERVISOR_DE_SEGURIDAD,   // Verifica el cumplimiento de las normas de seguridad
    SOPORTE_TECNICO,           // Técnico que da soporte en rutas críticas
    MONITOREADOR_DE_CARGA,     // Responsable de supervisar el estado de la carga
    MEDICO_DE_EMERGENCIA,      // Personal médico para emergencias
    ENTRENADOR,                // Entrenador o capacitador del conductor
    ESCOLTA_DE_SEGURIDAD,      // Personal de seguridad o escolta
    COORDINADOR_DE_RUTA        // Persona encargada de planificar y supervisar la ruta
}
