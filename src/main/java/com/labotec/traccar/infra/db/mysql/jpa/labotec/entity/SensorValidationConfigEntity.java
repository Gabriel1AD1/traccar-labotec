package com.labotec.traccar.infra.db.mysql.jpa.labotec.entity;

import com.labotec.traccar.domain.database.models.TypeSensor;
import com.labotec.traccar.domain.database.models.optimized.TypeValidation;
import com.labotec.traccar.domain.enums.DataType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tbl_validacion_sensor" , schema = "traccar_db")
public class SensorValidationConfigEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "usuario_id")
    private Long userId;
    @Column(name = "compañia_id")
    private Long companyId;
    @Column(name = "dispositivo_id")
    private Long deviceId;
    @Column(name = "estado")
    private Boolean state;
    @Column(name = "nombre_validacion")
    private String nameValidation;
    @Column(name = "nombre_sensor")
    private String nameSensor;  // Nombre del sensor (puede ser 'puerta', 'temperatura', etc.)
    @Column(name = "operador")
    private String operator;    // Operador para comparar el valor (por ejemplo, '=', '>', '<', '>=', etc.)
    @Column(name = "valor")
    private String value;
    @Column(name = "tipo_sensor")
    private TypeSensor typeSensor;
    @Column(name = "mensaje_alerta")
    private String messageAlert;
    @Column(name = "tipo_validacion")
    @Enumerated(EnumType.STRING)
    private TypeValidation typeValidation;
    @Enumerated(EnumType.STRING) // Enum mapeado como texto en la base de datos
    @Column(name = "tipo_dato")
    private DataType dataType;  // Tipo de dato (INT, DOUBLE, TEXT)
}

