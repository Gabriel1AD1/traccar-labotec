package com.labotec.traccar.app.implementation;

import com.labotec.traccar.app.ports.input.repository.OptimizedSensorValidationConfigRepository;
import com.labotec.traccar.app.ports.input.repository.SensorDeviceRepository;
import com.labotec.traccar.domain.database.models.optimized.OptimizedSensorValidationConfig;
import com.labotec.traccar.domain.database.models.optimized.TypeValidation;
import com.labotec.traccar.domain.embebed.ValidatorTime;
import com.labotec.traccar.domain.enums.DataType;
import com.labotec.traccar.domain.web.dto.traccar.DeviceRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.labotec.traccar.app.constants.OperatorConstant.*;

/**
 * Clase ValidateParams
 * Responsable de validar los parámetros de sensores según las configuraciones
 * definidas. Implementa validaciones de tipo tiempo acumulado (TIME) y
 * estáticas (STATIC) basadas en los valores actuales y configurados.
 */
@AllArgsConstructor
@Service
public class ValidateParams {

    private final OptimizedSensorValidationConfigRepository repository;
    private final SensorDeviceRepository sensorDeviceRepository;

    /**
     * Valida los parámetros de un dispositivo basado en las configuraciones
     * de validación disponibles.
     *
     * @param deviceRequestDTO Objeto que contiene los datos del dispositivo, incluidos
     *                         los valores actuales de los sensores.
     */
    public void validateParams(DeviceRequestDTO deviceRequestDTO) {
        // Obtener las configuraciones de validación para el dispositivo
        List<OptimizedSensorValidationConfig> validationConfigs = repository.findAllByDeviceId(deviceRequestDTO.getDeviceId());

        // Iterar sobre las configuraciones y validar cada parámetro del dispositivo
        for (OptimizedSensorValidationConfig config : validationConfigs) {
            String sensorName = config.getNameSensor(); // Nombre del sensor
            String operator = config.getOperator();     // Operador de validación
            String expectedValue = config.getValue();   // Valor esperado
            String messageError = config.getMessageError(); // Mensaje de error personalizado
            DataType dataType = config.getDataType();   // Tipo de dato (INT, DOUBLE, TEXT)

            // Obtener el valor del sensor desde el mapa de atributos
            Object sensorValue = deviceRequestDTO.getAttributes().get(sensorName);

            TypeValidation typeValidation = config.getTypeValidation();

            switch (typeValidation) {
                case TIME:
                    var sensorDeviceUpdated = sensorDeviceRepository.updateAndReturn(deviceRequestDTO.getDeviceId(), sensorName, sensorValue.toString());

                    Long timeAcumulated = sensorDeviceUpdated.getTimeAcumulated();
                    String currentState = sensorDeviceUpdated.getStateCurrent();
                    ValidatorTime validatorTimeConfig = config.getValidatorTime();
                    var sateVerify = validatorTimeConfig.getValueEvaluation();
                    String valueVerify = validatorTimeConfig.getValueEvaluation();
                    if (Objects.equals(currentState, sateVerify)){
                        boolean isValidTime = validateSensor(timeAcumulated, operator, valueVerify, dataType);
                        if (!isValidTime) {
                            System.out.println(messageError + sensorName);
                            return;
                        }
                    }


                    break;

                case STATIC:
                    // Realizar la validación
                    boolean isValidStatic = validateSensor(sensorValue, operator, expectedValue, dataType);

                    if (isValidStatic) {
                        System.out.println(messageError + sensorName);
                        return;
                    } else {
                        System.out.println(messageError + sensorName);
                    }
                    break;

                default:
                    throw new IllegalArgumentException("Tipo de validación no soportado: " + typeValidation);
            }
        }
    }

    /**
     * Valida un sensor específico basado en el tipo de dato, operador y valor esperado.
     *
     * @param sensorValue   Valor actual del sensor.
     * @param operator      Operador de validación (e.g., =, !=, >).
     * @param expectedValue Valor esperado configurado.
     * @param dataType      Tipo de dato del sensor (INT, DOUBLE, TEXT).
     * @return true si la validación es exitosa, false en caso contrario.
     */
    private boolean validateSensor(Object sensorValue, String operator, String expectedValue, DataType dataType) {
        if (sensorValue == null) {
            System.out.println("El sensor no tiene un valor presente.");
            return false;
        }

        try {
            switch (dataType) {
                case INT:
                    int actualInt = Integer.parseInt(sensorValue.toString());
                    int expectedInt = Integer.parseInt(expectedValue);
                    return compare(actualInt, expectedInt, operator);

                case DOUBLE:
                    double actualDouble = Double.parseDouble(sensorValue.toString());
                    double expectedDouble = Double.parseDouble(expectedValue);
                    return compare(actualDouble, expectedDouble, operator);

                case TEXT:
                    String actualText = sensorValue.toString();
                    return compareText(actualText, expectedValue, operator);

                default:
                    throw new IllegalArgumentException("Tipo de dato no soportado: " + dataType);
            }
        } catch (Exception e) {
            System.err.println("Error al validar el sensor: " + e.getMessage());
            return false;
        }
    }

    /**
     * Compara dos valores numéricos usando el operador especificado.
     *
     * @param actualValue   Valor actual del sensor.
     * @param expectedValue Valor esperado configurado.
     * @param operator      Operador de validación (e.g., >, >=, <).
     * @param <T>           Tipo del valor (INT o DOUBLE).
     * @return true si la comparación es válida, false en caso contrario.
     */
    private <T extends Comparable<T>> boolean compare(T actualValue, T expectedValue, String operator) {
        return switch (operator) {
            case EQUAL -> actualValue.compareTo(expectedValue) == 0;
            case NOT_EQUAL -> actualValue.compareTo(expectedValue) != 0;
            case LESS_THAN -> actualValue.compareTo(expectedValue) < 0;
            case GREATER_THAN -> actualValue.compareTo(expectedValue) > 0;
            case LESS_THAN_OR_EQUAL -> actualValue.compareTo(expectedValue) <= 0;
            case GREATER_THAN_OR_EQUAL -> actualValue.compareTo(expectedValue) >= 0;
            default -> throw new IllegalArgumentException("Operador no soportado: " + operator);
        };
    }

    /**
     * Compara dos valores de texto usando el operador especificado.
     *
     * @param actualValue   Valor actual del sensor.
     * @param expectedValue Valor esperado configurado.
     * @param operator      Operador de validación (e.g., =, !=).
     * @return true si la comparación es válida, false en caso contrario.
     */
    private boolean compareText(String actualValue, String expectedValue, String operator) {
        return switch (operator) {
            case "=" -> actualValue.equals(expectedValue);
            case "!=" -> !actualValue.equals(expectedValue);
            default -> throw new IllegalArgumentException("Operador no soportado para texto: " + operator);
        };
    }
}
