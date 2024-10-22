package com.labotec.traccar.domain.database.models;

import lombok.*;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Route {
    private Integer id;
    private String name;
    private Byte status;
    private BusStop originBusStop;
    private BusStop destinationBusStop;
    private Company company;
    private Instant lastModifiedDate;
    private Instant createdDate;
}
