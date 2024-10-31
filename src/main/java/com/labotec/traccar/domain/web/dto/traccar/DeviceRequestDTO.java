package com.labotec.traccar.domain.web.dto.traccar;

import lombok.Data;

import java.util.Date;

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
    private double altitude; // value in meters
    private double speed; // value in knots
    private double course;
    private String address;
    private double accuracy;
}
