package com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.process;

import com.labotec.traccar.domain.database.models.TypeSensor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_parametros")
public class ParameterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private TypeSensor typeSensors;
    private String description;
    private String dataType;
    private boolean required;

    @ManyToOne
    @JoinColumn(name = "device_id", nullable = false)
    private DeviceEntity device;

    @OneToMany(mappedBy = "parameter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RuleEntity> rules = new ArrayList<>();

    public ParameterEntity(String name, TypeSensor typeSensors, String description, String dataType, boolean required) {
        this.name = name;
        this.typeSensors = typeSensors;
        this.description = description;
        this.dataType = dataType;
        this.required = required;
    }

    public void addRule(RuleEntity rule) {
        rules.add(rule);
        rule.setParameter(this);
    }

}