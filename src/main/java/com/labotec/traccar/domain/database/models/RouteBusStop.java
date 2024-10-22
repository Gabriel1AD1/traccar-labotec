package com.labotec.traccar.domain.database.models;
import lombok.*;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteBusStop {
    private Integer id;
    private Route route;
    private BusStop busStop;
    private Integer order;
    private Instant createdDate;
    private Instant lastModifiedDate;
}