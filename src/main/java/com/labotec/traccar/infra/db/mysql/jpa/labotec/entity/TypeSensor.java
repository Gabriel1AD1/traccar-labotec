package com.labotec.traccar.infra.db.mysql.jpa.labotec.entity;

public enum TypeSensor {
    DOOR,           // Sensor de puerta (útil para rastreo de apertura/cierre en vehículos)
    SPEED,          // Sensor de velocidad
    BATTERY,        // Sensor de batería del dispositivo GPS
    ALTITUDE,       // Sensor de altitud
    LONGITUDE,      // Sensor de longitud
    LATITUDE,       // Sensor de latitud
    SATELLITE,      // Sensor de señal de satélite
    GPS_SIGNAL,     // Calidad de la señal GPS
    HDOP,           // Precisión horizontal (Horizontal Dilution of Precision)
    VDOP,           // Precisión vertical (Vertical Dilution of Precision)
    PDOP,           // Precisión de posición (Position Dilution of Precision)
    GNSS,           // Número de satélites visibles (Sistema Global de Navegación por Satélite)
    COURSE,         // Dirección de movimiento o rumbo
    GEO_FENCE,      // Entrada/salida de zona geográfica definida
    GPS_ACCURACY,   // Precisión de la posición GPS
    TIMESTAMP,      // Marca de tiempo del dato GPS
    HEADING,        // Dirección de viaje basada en la orientación
    DISTANCE,       // Distancia recorrida desde el último punto registrado
    SIGNAL_STRENGTH,// Intensidad de la señal de GPS o GNSS
    TEMPERATURE
}

