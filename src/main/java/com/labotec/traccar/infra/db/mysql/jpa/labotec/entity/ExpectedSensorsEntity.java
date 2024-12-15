package com.labotec.traccar.infra.db.mysql.jpa.labotec.entity;

import com.labotec.traccar.domain.database.models.TypeSensor;
import com.labotec.traccar.domain.enums.DataType;
import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
@Table(
        name = "tbl_sensores_esperados",
        schema = "traccar_db",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id_dispositivo", "nombre_sensor"})
        }
)
public class ExpectedSensorsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_usuario", nullable = false)
    private Long userId;

    @Column(name = "id_compania", nullable = false)
    private Long companyId;

    @Column(name = "id_dispositivo", nullable = false)
    private Long deviceId;

    @Column(name = "descripcion_sensor")
    private String descriptionSensor;

    @Column(name = "nombre_sensor", nullable = false)
    private String nameSensor;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_sensor", nullable = false)
    private TypeSensor typeSensor;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_dato", nullable = false)
    private DataType dataType;
}
