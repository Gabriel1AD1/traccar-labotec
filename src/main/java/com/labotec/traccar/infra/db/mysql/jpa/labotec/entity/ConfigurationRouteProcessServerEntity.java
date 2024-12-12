package com.labotec.traccar.infra.db.mysql.jpa.labotec.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "configuracion_procesador_rutas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfigurationRouteProcessServerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "radio_validar_paradero")
    private Double radiusValidateBusStop;
    @Column(name = "configuracion_primaria")
    private Boolean configurationPrimary;
}
