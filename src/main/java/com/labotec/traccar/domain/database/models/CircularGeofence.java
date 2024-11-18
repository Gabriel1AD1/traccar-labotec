package com.labotec.traccar.domain.database.models;


import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class CircularGeofence {

    private Long id;
    private User userId;
    private Company companyId;
    private String name;
    private String description;
    private Double latitude;
    private Double longitude;
    private Double radius;
    private Instant lastModifiedDate;
    private Instant createdDate;

}