package com.labotec.traccar.domain.database.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OverviewPolyline {
    private Long id;
    private RouteBusStop routeBusStop; // Referencia a tabla intermedia
    private String polyline;
    private Boolean isPrimary;
    private Instant lastModifiedDate;
    private Instant createdDate;
}
