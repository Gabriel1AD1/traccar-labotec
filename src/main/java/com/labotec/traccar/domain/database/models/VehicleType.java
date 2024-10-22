package com.labotec.traccar.domain.database.models;


import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class VehicleType {

    private Integer id;

    private String name;

    private Instant createdDate;

    private Instant lastModifiedDate;
}
