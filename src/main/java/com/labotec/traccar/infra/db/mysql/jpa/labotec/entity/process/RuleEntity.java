package com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.process;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RuleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String parameter;
    private String operator;
    private double threshold;
    private String alertMessage;

    @ManyToOne
    @JoinColumn(name = "parameter_id", nullable = false)
    private ParameterEntity parameterEntity;

    public RuleEntity(String parameter, String operator, double threshold, String alertMessage) {
        this.parameter = parameter;
        this.operator = operator;
        this.threshold = threshold;
        this.alertMessage = alertMessage;
    }

    public boolean evaluate(Map<String, Object> values) {
        if (!values.containsKey(parameter)) {
            return false;
        }

        Object value = values.get(parameter);

        if (value instanceof Number) {
            double numValue = ((Number) value).doubleValue();
            return evaluateCondition(numValue);
        } else if (value instanceof Boolean && (threshold == 1.0 || threshold == 0.0)) {
            boolean boolValue = (Boolean) value;
            boolean boolThreshold = threshold == 1.0;
            return evaluateCondition(boolValue, boolThreshold);
        }
        return false;
    }

    private boolean evaluateCondition(double value) {
        return switch (operator) {
            case ">" -> value > threshold;
            case "<" -> value < threshold;
            case ">=" -> value >= threshold;
            case "<=" -> value <= threshold;
            case "==" -> value == threshold;
            default -> throw new IllegalArgumentException("Operador no soportado: " + operator);
        };
    }

    private boolean evaluateCondition(boolean value, boolean threshold) {
        return operator.equals("==") && value == threshold;
    }

    public void setParameter(ParameterEntity parameter) {
        this.parameterEntity = parameter;
    }
}