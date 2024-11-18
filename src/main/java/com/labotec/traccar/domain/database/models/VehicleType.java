package com.labotec.traccar.domain.database.models;


import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class VehicleType {
    private Long id;
    private User userId;
    private Company companyId;
    private String name;
    private Instant createdDate;
    private String description;
    private Instant lastModifiedDate;
}
