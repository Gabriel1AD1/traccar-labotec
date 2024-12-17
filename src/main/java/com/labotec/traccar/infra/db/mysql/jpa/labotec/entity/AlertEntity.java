package com.labotec.traccar.infra.db.mysql.jpa.labotec.entity;

import com.labotec.traccar.domain.enums.AlertType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_alerts")
public class AlertEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "alert_type", nullable = false, length = 50)
    private AlertType alertType; // Usar el enum para el tipo de alerta

    @Column(name = "description", nullable = false, length = 255)
    private String description;

    @Column(name = "vehicle_id", nullable = true)
    private Long vehicleId;

    @Column(name = "user_id", nullable = true)
    private Long userId;

    @Column(name = "latitude", nullable = true)
    private Double latitude;

    @Column(name = "longitude", nullable = true)
    private Double longitude;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

}
