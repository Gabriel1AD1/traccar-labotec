package com.labotec.traccar.domain.database.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.Instant;

@Data
public class CircularGeofence {

    private Long id;
    @JsonIgnore
    private User userId;
    @JsonIgnore
    private Company companyId;
    private String name;
    private String description;
    private Double latitude;
    private Double longitude;
    private Double radius;
    @JsonIgnore
    private Instant lastModifiedDate;
    @JsonIgnore
    private Instant createdDate;

}