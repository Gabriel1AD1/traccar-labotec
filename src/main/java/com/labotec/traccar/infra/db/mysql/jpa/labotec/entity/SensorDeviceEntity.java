package com.labotec.traccar.infra.db.mysql.jpa.labotec.entity;

import com.labotec.traccar.domain.database.models.TypeSensor;
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
@SqlResultSetMapping(
        name = "SensorDeviceMapping",
        entities = @EntityResult(
                entityClass = SensorDeviceEntity.class,
                fields = {
                        @FieldResult(name = "id", column = "id"),
                        @FieldResult(name = "deviceId", column = "dispositivo_id"),
                        @FieldResult(name = "sensorName", column = "nombre_sensor"),
                        @FieldResult(name = "typeSensor", column = "tipo_sensor"),
                        @FieldResult(name = "dataType", column = "tipo_dato"),
                        @FieldResult(name = "stateCurrent", column = "estado_actual"),
                        @FieldResult(name = "initStateCurrent", column = "inicio_estado_actual"),
                        @FieldResult(name = "timeAcumulated", column = "tiempo_acumulado")
                }
        )
)
@NamedNativeQuery(
        name = "UpdateSensorStateAndReturn",
        resultSetMapping = "SensorDeviceMapping",
        query = "CALL UpdateSensorStateAndReturn(:p_dispositivo_id, :p_nombre_sensor, :p_estado_nuevo)"
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
