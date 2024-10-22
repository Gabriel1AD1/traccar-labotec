package com.labotec.traccar.infra.db.mysql.jpa.traccar.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT) // Solo incluir campos que no están en su valor por defecto
public class Attributes {

    // Deserializa con "sat", pero serializa con "satellite_count"
    @JsonProperty("satellite_count")
    @JsonAlias("sat")
    private int satelliteCount;

    // Deserializa con "hdop", pero serializa con "horizontal_dilution_of_precision"
    @JsonProperty("horizontal_dilution_of_precision")
    @JsonAlias("hdop")
    private double horizontalDilutionOfPrecision;

    // Para "input", serializa con "digital_input_status", pero acepta "input" en la entrada
    @JsonProperty("digital_input_status")
    @JsonAlias("input")
    private String digitalInputStatus;

    // Otros campos similares
    @JsonProperty("analog_input_battery_vehicle")
    @JsonAlias("adc1")
    private String analogInputBatteryVehicle;

    @JsonProperty("analog_input_battery_modem")
    @JsonAlias("adc2")
    private String analogInputBatteryModem;

    @JsonProperty("cellular_signal_quality")
    @JsonAlias("csq")
    private int cellularSignalQuality;

    @JsonProperty("temp_sensor_left1_temp")
    @JsonAlias("th_l1_t")
    private double tempSensorLeft1Temp;

    @JsonProperty("temp_sensor_left1_battery")
    @JsonAlias("th_l1_b")
    private double tempSensorLeft1Battery;

    @JsonProperty("temp_sensor_right1_temp")
    @JsonAlias("th_r1_t")
    private double tempSensorRight1Temp;

    @JsonProperty("temp_sensor_right1_battery")
    @JsonAlias("th_r1_b")
    private double tempSensorRight1Battery;

    @JsonProperty("door_sensor_right1_status")
    @JsonAlias("d_r1_s")
    private int doorSensorRight1Status;

    @JsonProperty("door_sensor_right3_status")
    @JsonAlias("d_r3_s")
    private int doorSensorRight3Status;

    @JsonProperty("door_sensor_left1_status")
    @JsonAlias("d_l1_s")
    private int doorSensorLeft1Status;

    @JsonProperty("door_sensor_left3_status")
    @JsonAlias("d_l3_s")
    private int doorSensorLeft3Status;

    @JsonProperty("door_sensor_right2_status")
    @JsonAlias("d_r2_s")
    private int doorSensorRight2Status;

    @JsonProperty("door_sensor_left2_status")
    @JsonAlias("d_l2_s")
    private int doorSensorLeft2Status;

    @JsonProperty("distance_covered")
    @JsonAlias("distance")
    private double distanceCovered;

    @JsonProperty("total_distance_covered")
    @JsonAlias("totalDistance")
    private double totalDistanceCovered;

    @JsonProperty("motion_detected")
    @JsonAlias("motion")
    private boolean motionDetected;
}
