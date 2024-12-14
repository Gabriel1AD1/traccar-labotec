package com.labotec.traccar.infra.db.mysql.jpa.labotec.entity;

import com.labotec.traccar.domain.enums.DataType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_sensor_dispositivo")
@SqlResultSetMapping(name = "MSensorDeviceEntity", classes =
@ConstructorResult(targetClass = SensorDeviceEntity.class, columns = {
        @ColumnResult(name = "id", type = Long.class),
        @ColumnResult(name = "dispositivo_id", type = Long.class),
        @ColumnResult(name = "nombre_sensor", type = String.class),
        @ColumnResult(name = "tipo_sensor", type = String.class),
        @ColumnResult(name = "tipo_dato", type = String.class),
        @ColumnResult(name = "estado_actual", type = String.class),
        @ColumnResult(name = "inicio_estado_actual", type = java.time.Instant.class),
        @ColumnResult(name = "tiempo_acumulado", type = Long.class)
}))
@NamedStoredProcedureQuery(
        name = "UpdateSensorStateAndReturn",
        resultSetMappings = "MSensorDeviceEntity",
        procedureName = "UpdateSensorStateAndReturn",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_dispositivo_id", type = Long.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_nombre_sensor", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_estado_nuevo", type = String.class)
        }
)
public class SensorDeviceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "dispositivo_id", nullable = false)
    private Long deviceId;

    @Column(name = "nombre_sensor", nullable = false)
    private String sensorName;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_sensor", nullable = false)
    private TypeSensor typeSensor; // 'tipo_sensor' en la BD

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_dato", nullable = false)
    private DataType dataType; // 'tipo_dato' en la BD

    @Column(name = "estado_actual", nullable = false)
    private String stateCurrent; // 'estado_actual' en la BD

    @Column(name = "inicio_estado_actual", nullable = false)
    private Instant initStateCurrent; // 'inicio_estado_actual' en la BD

    @Column(name = "tiempo_acumulado", nullable = false)
    private Long timeAcumulated ;
    @PrePersist
    private void addValue(){
        if (this.timeAcumulated== null){
            timeAcumulated = 0L;
        }
    }
}
