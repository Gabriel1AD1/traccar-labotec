package com.labotec.traccar.domain.database.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class VehicleType {
    private Long id;
    @JsonIgnore
    private User userId;
    @JsonIgnore
    private Company companyId;
    private String name;

    @JsonIgnore
    private Instant createdDate;

    private String description;
    @JsonIgnore
    private Instant lastModifiedDate;
}
