package com.labotec.traccar.app.implementation;

import com.labotec.traccar.app.ports.input.notification.NotificationTraccar;
import com.labotec.traccar.app.ports.input.repository.AlertValidParamsRepository;
import com.labotec.traccar.app.ports.input.repository.SensorDeviceRepository;
import com.labotec.traccar.app.ports.input.repository.SensorValidatorRepository;
import com.labotec.traccar.app.ports.input.repository.VehicleRepository;
import com.labotec.traccar.domain.database.models.AlertValidParams;
import com.labotec.traccar.domain.database.models.optimized.OptimizedSensorValidationConfig;
import com.labotec.traccar.domain.database.models.optimized.TypeValidation;
import com.labotec.traccar.domain.embebed.RangeValue;
import com.labotec.traccar.domain.embebed.ValidatorTime;
import com.labotec.traccar.domain.enums.DataType;
import com.labotec.traccar.domain.web.labotec.notify.NotificationDTO;
import com.labotec.traccar.domain.web.traccar.DeviceRequestDTO;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
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

    private final SensorValidatorRepository repository;
    private final SensorDeviceRepository sensorDeviceRepository;
    private final NotificationTraccar notificationTraccar;
    private final VehicleRepository vehicleRepository;
    private final AlertValidParamsRepository alertValidParamsRepository;
    private static final Logger logger = LoggerFactory.getLogger(ValidateParams.class);

    /**
     * Valida los parámetros de un dispositivo basado en las configuraciones
     * de validación disponibles.
     *
     * @param deviceRequestDTO Objeto que contiene los datos del dispositivo, incluidos
     *                         los valores actuales de los sensores.
     */
    public void validateParams(DeviceRequestDTO deviceRequestDTO) {
        String licencePlate = vehicleRepository.getLicencePlateByDeviceId(deviceRequestDTO.getDeviceId());
        // Obtener las configuraciones de validación para el dispositivo
        List<OptimizedSensorValidationConfig> validationConfigs = repository.findAllByDeviceId(deviceRequestDTO.getDeviceId());
        String title= "Validacion Fallida para el vehiculo " +licencePlate;
        // Iterar sobre las configuraciones y validar cada parámetro del dispositivo
        for (OptimizedSensorValidationConfig config : validationConfigs) {
            Long userId = config.getUserId();
            String sensorName = config.getNameSensor(); // Nombre del sensor
            String operator = config.getOperator();     // Operador de validación
            String expectedValue = config.getValue();   // Valor esperado
            String messageError = config.getMessageError(); // Mensaje de error personalizado
            DataType dataType = config.getDataType();   // Tipo de dato (INT, DOUBLE, TEXT)

            Map<String,Object> attributes = deviceRequestDTO.getAttributes();

            // Obtener el valor del sensor desde el mapa de atributos
            Object sensorValue = deviceRequestDTO.getAttributes().get(sensorName);
            if (sensorValue== null){
                sendAlert(createNotificationDTO(userId,"Sensor no encontrado " , "El sensor " + config.getNameSensor() + "No ha sido encontrado"));
                continue;
            }
            TypeValidation typeValidation = config.getTypeValidation();

            switch (typeValidation) {
                case TIME:
                    var sensorDeviceUpdated = sensorDeviceRepository.updateAndReturn(deviceRequestDTO.getDeviceId(), sensorName, sensorValue.toString());

                    Long timeAcumulated = sensorDeviceUpdated.getTimeAcumulated();
                    String currentState = sensorDeviceUpdated.getStateCurrent();
                    ValidatorTime validatorTimeConfig = config.getValidatorTime();
                    String sateVerify = validatorTimeConfig.getStateValidation();
                    Long valueVerify = validatorTimeConfig.getValueEvaluation();
                    //Verificar si ya se ha enviado una solicitud de websocket
                    boolean sendAlert =  timeAcumulated >= valueVerify;
                    if (sendAlert){
                        break;
                    }
                    if (Objects.equals(currentState, sateVerify)){
                        boolean isValidTime = validateSensor(timeAcumulated, operator, valueVerify.toString(), DataType.INT);
                        if (!isValidTime ) {
                            sendAlert(createNotificationDTO(userId,title,messageError));
                        }
                    }
                    alertValidParamsRepository.save(createAlert(typeValidation, deviceRequestDTO.getDeviceId(),config.getId(),sensorValue.toString()));
                    break;
                case STATIC:
                    // Realizar la validación
                    boolean isValidStatic = validateSensor(sensorValue, operator, expectedValue, dataType);

                    if (!isValidStatic) {
                        sendAlert(createNotificationDTO(userId,title,messageError));
                    }
                    alertValidParamsRepository.save(createAlert(typeValidation, deviceRequestDTO.getDeviceId(),config.getId(),sensorValue.toString()));
                    break;
                case EXIST:
                    boolean isValidParams= validateAttributeExistenceAndType(attributes, sensorName, dataType);
                    if (!isValidParams){
                        sendAlert(createNotificationDTO(userId,title,messageError));
                    }
                    alertValidParamsRepository.save(createAlert(typeValidation, deviceRequestDTO.getDeviceId(),config.getId(),sensorValue.toString()));
                    break;
                case RANGE:
                    RangeValue rangeValue = config.getRangeValue();
                    Double initValue = rangeValue.getRangeA();
                    Double finishValue = rangeValue.getRangeB();
                    Double value =  Double.parseDouble(String.valueOf(sensorValue));
                    if (initValue < value && value > finishValue) {
                        sendAlert(createNotificationDTO(userId,title,messageError));
                    }
                    alertValidParamsRepository.save(createAlert(typeValidation, deviceRequestDTO.getDeviceId(),config.getId(),sensorValue.toString()));
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de validación no soportado: " + typeValidation);
            }
        }
    }
    /**
     * Valida la existencia del atributo en el mapa de atributos y su tipo de dato.
     *
     * @param attributes Mapa que contiene los atributos del dispositivo.
     * @param nameSensor Nombre del sensor a validar (clave en el mapa de atributos).
     * @param dataType Tipo de dato esperado para el valor del atributo.
     * @return true si el atributo existe y coincide con el tipo de dato, false de lo contrario.
     */
    private boolean validateAttributeExistenceAndType(Map<String, Object> attributes, String nameSensor, DataType dataType) {
        // Validar la existencia del atributo
        if (!attributes.containsKey(nameSensor)) {
            return false; // El atributo no existe
        }

        // Validar el tipo de dato si el atributo existe
        Object attributeValue = attributes.get(nameSensor);

        return switch (dataType) {
            case DOUBLE -> attributeValue instanceof Double;
            case INT -> attributeValue instanceof Integer;
            case TEXT -> attributeValue instanceof String;
            default -> false; // Tipo no soportado
        };
    }

    private AlertValidParams createAlert(TypeValidation typeValidation,
     Long deviceId,
     Long idValidation,
     String currentValue){
        return AlertValidParams.builder()
                .createAt(Instant.now())
                .idValidation(idValidation)
                .typeValidation(typeValidation)
                .deviceId(deviceId)
                .currentValue(currentValue)
                .build();
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
            logger.warn("El sensor no tiene un valor presente.");
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
            logger.warn("Error al validar el sensor: {}", e.getMessage());
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
    private void sendAlert(NotificationDTO notificationDTO){
        notificationTraccar.sendNotification(notificationDTO);
    }
    private NotificationDTO createNotificationDTO(Long userId, String title , String description){
        return NotificationDTO.builder()
                .userId(userId)
                .title(title)
                .message(description).build();
    }
}
