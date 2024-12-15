package com.labotec.traccar.domain.database.models;

public enum TypeSensor {
    // Sensores existentes
    DOOR,                // Sensor de puerta (útil para rastreo de apertura/cierre en vehículos)
    SPEED,               // Sensor de velocidad
    BATTERY,             // Sensor de batería del dispositivo GPS
    ALTITUDE,            // Sensor de altitud
    LONGITUDE,           // Sensor de longitud
    LATITUDE,            // Sensor de latitud
    SATELLITE,           // Sensor de señal de satélite
    GPS_SIGNAL,          // Calidad de la señal GPS
    HDOP,                // Precisión horizontal (Horizontal Dilution of Precision)
    VDOP,                // Precisión vertical (Vertical Dilution of Precision)
    PDOP,                // Precisión de posición (Position Dilution of Precision)
    GNSS,                // Número de satélites visibles (Sistema Global de Navegación por Satélite)
    COURSE,              // Dirección de movimiento o rumbo
    GEO_FENCE,           // Entrada/salida de zona geográfica definida
    GPS_ACCURACY,        // Precisión de la posición GPS
    TIMESTAMP,           // Marca de tiempo del dato GPS
    HEADING,             // Dirección de viaje basada en la orientación
    DISTANCE,            // Distancia recorrida desde el último punto registrado
    SIGNAL_STRENGTH,     // Intensidad de la señal de GPS o GNSS
    TEMPERATURE,         // Sensor de temperatura

    // Nuevos sensores generales
    ACCELERATION,        // Sensor de aceleración
    GYROSCOPE,           // Sensor de giroscopio
    HUMIDITY,            // Sensor de humedad
    PRESSURE,            // Sensor de presión atmosférica
    CO2,                 // Sensor de dióxido de carbono (calidad del aire)
    VOC,                 // Sensor de compuestos orgánicos volátiles
    FUEL_LEVEL,          // Sensor de nivel de combustible
    OIL_PRESSURE,        // Sensor de presión de aceite del motor
    BRAKE_STATUS,        // Estado de los frenos (activado/desactivado)
    ENGINE_LOAD,         // Sensor de carga del motor
    RPM,                 // Revoluciones por minuto del motor
    EXTERNAL_VOLTAGE,    // Sensor de voltaje externo
    INTERNAL_TEMPERATURE,// Temperatura interna del dispositivo
    LIGHT,               // Sensor de luz (luminosidad)
    PROXIMITY,           // Sensor de proximidad
    SOUND_LEVEL,         // Sensor de nivel de sonido (decibelios)
    VIBRATION,           // Sensor de vibración (impactos o movimiento)
    WATER_LEVEL,         // Sensor de nivel de agua (para tanques o inundaciones)

    // Nuevos sensores específicos
    TIRE_PRESSURE,       // Sensor de presión de llantas (útil para monitorear neumáticos)
    TIRE_TEMPERATURE     // Sensor de temperatura de las llantas
}
