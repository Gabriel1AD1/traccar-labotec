package com.labotec.traccar.domain.enums;

/**
 * Enum que representa los diferentes tipos de alertas que el sistema puede generar.
 */
public enum AlertType {

    /**
     * El tiempo programado no se completó.
     */
    TIME_NOT_COMPLETED,

    /**
     * Violación de una geocerca establecida.
     */
    GEOFENCE_VIOLATION,

    /**
     * Exceso de velocidad detectado.
     */
    SPEEDING,

    /**
     * Dispositivo desconectado del sistema.
     */
    DEVICE_DISCONNECTED,

    /**
     * Violación detectada por un sensor (e.g., apertura de puertas, movimiento inesperado).
     */
    SENSOR_VIOLATION,

    /**
     * Nivel de combustible bajo.
     */
    FUEL_LEVEL_LOW,

    /**
     * Batería del vehículo baja.
     */
    BATTERY_LOW,

    /**
     * Motor sobrecalentado.
     */
    ENGINE_OVERHEATED,

    /**
     * Puerta del vehículo abierta mientras está en movimiento.
     */
    DOOR_OPEN,

    /**
     * Aceleración brusca detectada.
     */
    HARSH_ACCELERATION,

    /**
     * Frenado brusco detectado.
     */
    HARSH_BRAKING,

    /**
     * Presión de los neumáticos baja.
     */
    TIRE_PRESSURE_LOW,

    /**
     * Desviación de la ruta planificada detectada.
     */
    ROUTE_DEVIATION,

    /**
     * Entrada a una geocerca específica.
     */
    GEOFENCE_ENTER,

    /**
     * Salida de una geocerca específica.
     */
    GEOFENCE_EXIT,

    /**
     * Tiempo de parada excedido en una ubicación no autorizada.
     */
    STOP_OVER_LIMIT,

    /**
     * Acceso no autorizado al vehículo.
     */
    UNAUTHORIZED_ACCESS,

    /**
     * Robo del vehículo detectado.
     */
    VEHICLE_THEFT_DETECTED,

    /**
     * Botón de pánico presionado por el conductor.
     */
    PANIC_BUTTON_PRESSED,

    /**
     * Fatiga detectada en el conductor, posiblemente mediante sensores o cámaras.
     */
    DRIVER_FATIGUE,

    /**
     * Falla en algún sensor del sistema.
     */
    SENSOR_MALFUNCTION,

    /**
     * Dispositivo del sistema sobrecalentado.
     */
    DEVICE_OVERHEATED,

    /**
     * Pérdida de datos en el dispositivo.
     */
    DATA_LOSS,

    /**
     * Pérdida de señal GPS detectada.
     */
    GPS_SIGNAL_LOST,

    /**
     * Desconexión de red detectada.
     */
    NETWORK_DISCONNECTED,

    /**
     * Mantenimiento programado requerido para el vehículo.
     */
    MAINTENANCE_REQUIRED,

    /**
     * Cambio de aceite necesario para el vehículo.
     */
    OIL_CHANGE_REQUIRED,

    /**
     * Inspección de frenos necesaria.
     */
    BRAKE_INSPECTION_REQUIRED,

    /**
     * Rotación de neumáticos requerida.
     */
    TIRE_ROTATION_REQUIRED,

    /**
     * Límite de velocidad excedido en una zona específica.
     */
    SPEED_LIMIT_EXCEEDED,

    /**
     * Tiempo en ralentí excedido.
     */
    IDLE_TIME_EXCEEDED,

    /**
     * Conducción en reversa detectada fuera de un área permitida.
     */
    REVERSE_DRIVING,

    /**
     * Apertura de puerta del compartimento de carga detectada.
     */
    CARGO_DOOR_OPEN,

    /**
     * Movimiento inesperado de la carga detectado.
     */
    CARGO_MOVEMENT_DETECTED
}

