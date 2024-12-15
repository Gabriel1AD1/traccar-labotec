package com.labotec.traccar.infra.db.mysql.jpa.labotec.entity;

import com.labotec.traccar.domain.database.models.optimized.TypeValidation;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "tbl_alert_valid_params", schema = "traccar_db")
public class AlertValidParamsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_validacion", nullable = false)
    private TypeValidation typeValidation;
    @Column(name = "dispositivo_id", nullable = false)
    private Long deviceId;
    @Column(name = "validacion_id", nullable = false)
    private Long idValidation;
    @Column(name = "valor_actual")
    private String currentValue;
    @Column(name = "hora_creacion")
    private Instant createAt;
    @PrePersist
    private void addValues(){
        if (this.createAt == null){
            this.createAt = Instant.now();
        }
    }

}
