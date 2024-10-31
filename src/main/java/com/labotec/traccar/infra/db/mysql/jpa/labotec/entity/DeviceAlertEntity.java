package com.labotec.traccar.infra.db.mysql.jpa.labotec.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "tc_device_alerts")
public class DeviceAlertEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_id", nullable = false)
    private long deviceId; // ID del dispositivo que genera la alerta

    @Column(name = "alert_time", nullable = false)
    private Instant alertTime; // Momento en que se generó la alerta

    @Column(name = "alert_type", nullable = false)
    private String alertType; // Tipo de alerta, por ejemplo, "Detención prolongada"

    @Column(name = "duration", nullable = true)
    private Integer duration; // Duración en minutos (opcional, si aplica)

    @Column(name = "latitude")
    private double latitude; // Latitud del dispositivo al momento de la alerta

    @Column(name = "longitude")
    private double longitude; // Longitud del dispositivo al momento de la alerta

    @Column(name = "speed")
    private double speed; // Velocidad al momento de la alerta

    @Column(name = "course")
    private double course; // Curso o dirección del dispositivo en el momento de la alerta

    @Column(name = "altitude")
    private double altitude; // Altitud al momento de la alerta

    @Column(name = "address", length = 512)
    private String address; // Dirección si se necesita para mejorar la ubicación

    @Column(name = "accuracy")
    private double accuracy; // Precisión de la ubicación

    @Column(name = "description", length = 1024)
    private String description; // Descripción detallada del motivo de la alerta
}
