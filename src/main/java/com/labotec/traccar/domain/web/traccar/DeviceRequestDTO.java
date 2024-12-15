package com.labotec.traccar.domain.web.traccar;

import lombok.Data;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class DeviceRequestDTO {
    private long deviceId;
    private String protocol;
    private Date serverTime;
    private Date deviceTime;
    private Date fixTime;
    private boolean outdated;
    private boolean valid;
    private double latitude;
    private double longitude;
    private double altitude;
    private double speed;
    private double course;
    private String address;
    private double accuracy;
    private Map<String, Object> attributes = new LinkedHashMap<>();

}
